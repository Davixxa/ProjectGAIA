import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Visualizer for the ant colony problem.
 * It keeps an internal representation of the spatial configuration of the graph, and includes a
 * method to update the visual display with the position of all ants and the levels of sugar and
 * pheromones in each node or edge.
 * <p>
 * The visualizer is semantically associated with an instance of {@code Simulator} that has
 * the same graph and list of ants as the visualizer.
 * In other words, the constructor to {@code Visualizer} should be called with the same
 * {@code Graph} and {@code Ant[]} arguments as the constructor to {@code Simulator} used in the
 * same simulation.
 * <p>
 * This requirement is motivated by the fact that {@code Visualizer} is an auxiliary class, whose
 * methods should in principle belong to {@code Simulator} objects; the separation in two classes
 * is only related to the purpose of this project, as implementing the methods included in
 * {@code Visualizer} does not fall under the expected learning outcomes of this course.
 */
public class Visualizer {
    private final Graph graph;
    private final boolean isGrid;
    private final int gridColumns, gridRows;
    private final Node[] nodes;
    private final Colony[] colonies;
    private final Ant[] ants;
    private final int[] antsPerNode;
    private final int[] antsPerColony;

    private final JFrame window;
    private final JTextArea status;
    private final JPanel grid;

    private static final int TICK_SLEEP = 1000; // delay for update() in ms

    /**
     * Constructor: takes a graph and a node in the graph and builds an internal representation of
     * it.<p>
     * <b>Precondition:</b> the start node must be part of the graph and the graph must be connected.
     *
     * @param graph  the graph to visualize
     * @param isGrid whether the graph is a rectangular grid of nodes at least 3 by 3
     * @param start  a node in the graph
     * @param ants   a reference to the array of ants used throughout the simulation
     */
    public Visualizer(Graph graph, boolean isGrid, Node start, Ant[] ants) {
        this.graph = graph;
        this.isGrid = isGrid;
        this.ants = ants;

        // find all nodes reachable from the given node;
        List<Colony> colonies = new LinkedList<>();
        if (isGrid) {
            // try to find a corner node
            List<Node> potential = new LinkedList<>();
            potential.add(start);
            boolean found = false;
            Node current = start; // dummy
            while (!found) {
                current = potential.remove(0);
                Node[] newNodes = graph.adjacentTo(current);
                if (newNodes.length == 2) {
                    found = true;
                } else {
                    for (Node n : newNodes) {
                        if (!potential.contains(n)) {
                            potential.add(n);
                        }
                    }
                }
            }
            // build the border of the grid
            Node[] neighbours = graph.adjacentTo(current);
            List<Node> top = getSideFrom(graph, current, neighbours[0]);
            List<Node> side = getSideFrom(graph, current, neighbours[1]);

            gridColumns = top.size();
            gridRows = side.size();
            Node[][] grid = new Node[gridColumns][gridRows];
            for (int i = 0; i < gridColumns; i++) {
                grid[i][0] = top.get(i);
            }
            for (int j = 0; j < gridRows; j++) {
                grid[0][j] = side.get(j);
            }
            for (int i = 0; i < gridColumns - 1; i++) {
                for (int j = 0; j < gridRows - 1; j++) {
                    Node[] adjacentX = graph.adjacentTo(grid[i + 1][j]);
                    Node[] adjacentY = graph.adjacentTo(grid[i][j + 1]);
                    grid[i + 1][j + 1] = findIntersection(adjacentX, adjacentY, grid[i][j]);
                }
            }
            // copy the grid in the array of nodes and find all colonies
            this.nodes = new Node[gridColumns * gridRows];
            for (int i = 0; i < gridColumns; i++) {
                for (int j = 0; j < gridRows; j++) {
                    this.nodes[i * gridRows + j] = grid[i][j];
                    if (grid[i][j] instanceof Colony) {
                        colonies.add((Colony) grid[i][j]);
                    }
                }
            }
        } else {
            gridColumns = gridRows = -1;
            // bfs visit
            Set<Node> alreadyVisited = new HashSet<>();
            Queue<Node> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                Node currentNode = queue.remove();
                alreadyVisited.add(currentNode);
                if (currentNode instanceof Colony) {
                    colonies.add((Colony) currentNode);
                }
                for (Node neighbour : graph.adjacentTo(currentNode)) {
                    if (!alreadyVisited.contains(neighbour) && !queue.contains(neighbour)) {
                        queue.add(neighbour);
                    }
                }
            }
            this.nodes = new Node[alreadyVisited.size()];
            alreadyVisited.toArray(this.nodes);
        }
        this.colonies = new Colony[colonies.size()];
        colonies.toArray(this.colonies);
        this.antsPerColony = new int[this.colonies.length];
        this.antsPerNode = new int[this.nodes.length];

        // GUI
        window = new JFrame("Ant simulation status");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // an area for displaying the current status as text (see status method)
        status = new JTextArea();
        status.setEditable(false);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        status.setFont(font);
        if (isGrid) {
            // a panel for drawing the grid of nodes
            grid = new JPanel(true) {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    renderGraph(g);
                }
            };
            grid.setPreferredSize(new Dimension(
                    gridColumns * boxWidth(grid.getFontMetrics(font)),
                    gridRows * boxHeight(grid.getFontMetrics(font))));
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    new JScrollPane(status),
                    new JScrollPane(grid));
            splitPane.setDividerLocation(200);
            window.getContentPane().add(splitPane, BorderLayout.CENTER);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            grid = null;
            window.getContentPane().add(status);
            window.setSize(300, 600);
        }
        window.setVisible(false);
    }

    /**
     * Finds the intersection of two arrays different from the given one.<p>
     *
     * @param array1 the first array
     * @param array2 the second array
     * @param notMe  the node to avoid
     * @return an element in both array1 and array2, different from notMe
     */
    private static Node findIntersection(Node[] array1, Node[] array2, Node notMe) {
        for (Node n1 : array1) {
            for (Node n2 : array2) {
                if (n1 == n2 && n1 != notMe) {
                    return n1;
                }
            }
        }
        return null;
    }

    /**
     * Finds a side given the two first nodes.
     *
     * @param graph the graph
     * @param fst   the first node (corner)
     * @param snd   the second node
     * @return the side of the grid until (and including) the next corner
     */
    private static List<Node> getSideFrom(Graph graph, Node fst, Node snd) {
        List<Node> side = new ArrayList<>();
        side.add(fst);
        side.add(snd);
        Node previous = fst, current = snd;
        Node[] adjacent = graph.adjacentTo(current);
        while (adjacent.length > 2) {
            for (Node n : adjacent) {
                if (graph.adjacentTo(n).length < 4 && n != previous) {
                    side.add(n);
                    previous = current;
                    current = n;
                    adjacent = graph.adjacentTo(current);
                    break; // AGH
                }
            }
        }
        return side;
    }

    /* Methods for computing scaling rendered elements based on the font. */

    private static int nodePadding(FontMetrics m) {
        return m.getHeight() / 2;
    }

    private static int nodeWidth(FontMetrics m) {
        return nodePadding(m) * 2 + m.stringWidth("AAAAAAAAAAAAAAAAAAAA");
    }

    private static int nodeHeight(FontMetrics m) {
        return nodePadding(m) * 2 + m.getHeight() * 4;
    }

    private static int edgeLabelWidth(FontMetrics m) {
        return edgeLabelWidth(m, "AAAAAAAA");
    }

    private static int edgeLabelWidth(FontMetrics m, String label) {
        return nodePadding(m) * 2 + m.stringWidth(label);
    }

    private static int edgeLabelHeight(FontMetrics m) {
        return nodePadding(m) * 2 + m.getHeight();
    }

    private static int boxWidth(FontMetrics m) {
        return nodeWidth(m) + edgeLabelWidth(m) + 20;
    }

    private static int boxHeight(FontMetrics m) {
        return nodeHeight(m) + edgeLabelHeight(m) + 20;
    }

    private void renderGraph(Graphics g) {
        if (isGrid) {
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            // render edges
            g.setColor(Color.BLACK);
            for (int c = 1; c < gridColumns; c++) {
                renderEdge(g, 0, c, false);
            }
            for (int r = 1; r < gridRows; r++) {
                renderEdge(g, r, 0, true);
                for (int c = 1; c < gridColumns; c++) {
                    renderEdge(g, r, c, false);
                    renderEdge(g, r, c, true);
                }

            }
            // render nodes
            for (int r = 0; r < gridRows; r++) {
                for (int c = 0; c < gridColumns; c++) {
                    renderNode(g, r, c);
                }
            }
        }
    }

    private void renderEdge(Graphics g, int r, int c, boolean vertical) {
        final FontMetrics m = g.getFontMetrics();
        final int boxWidth = boxWidth(m);
        final int boxHeight = boxHeight(m);
        final int offsetX = boxWidth / 2;
        final int offsetY = boxHeight / 2;
        final int x1 = offsetX + boxWidth * c;
        final int y1 = offsetY + boxHeight * r;
        final int x2, y2;
        final int f;
        if (vertical) {
            f = graph.pheromoneLevel(nodes[c * gridRows + r], nodes[c * gridRows + r - 1]);
            x2 = x1;
            y2 = y1 - boxHeight;
        } else {
            f = graph.pheromoneLevel(nodes[c * gridRows + r], nodes[c * gridRows + r - gridRows]);
            x2 = x1 - boxWidth;
            y2 = y1;
        }
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(Color.WHITE);
        if (f > 0) {
            // draw label
            final int edgeLabelWidth = edgeLabelWidth(m, Float.toString(f));
            final int edgeLabelHeight = edgeLabelHeight(m);
            final int nodePadding = nodePadding(m);
            final int xl = x1 + (x2 - x1 - edgeLabelWidth) / 2;
            final int yl = y1 + (y2 - y1 - edgeLabelHeight) / 2;
            g.fillRoundRect(xl, yl, edgeLabelWidth, edgeLabelHeight, 5, 5);
            g.setColor(Color.BLACK);
            g.drawRoundRect(xl, yl, edgeLabelWidth, edgeLabelHeight, 5, 5);
            g.drawString(Float.toString(f), xl + nodePadding, yl + nodePadding + m.getAscent());
        }
    }

    private void renderNode(Graphics g, int r, int c) {
        final FontMetrics m = g.getFontMetrics();
        final int boxWidth = boxWidth(m);
        final int boxHeight = boxHeight(m);
        final int nodeWidth = nodeWidth(m);
        final int nodeHeight = nodeHeight(m);
        final int nodePadding = nodePadding(m);
        int x = c * boxWidth + (boxWidth - nodeWidth) / 2;
        int y = r * boxHeight + (boxHeight - nodeHeight) / 2;
        final int k = c * gridRows + r;
        int h = -1;
        if (nodes[k] instanceof Colony) {
            do {
                h += 1;
            } while (nodes[k] != colonies[h]);
            if (antsPerColony[h] == 0) {
                g.setColor(new Color(0xF08080));
            } else {
                g.setColor(new Color(0x90EE90));
            }
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRoundRect(x, y, nodeWidth, nodeHeight, nodePadding + 2, nodePadding + 2);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, nodeWidth, nodeHeight, nodePadding + 2, nodePadding + 2);
        String text = "";
        if (h != -1) {
            text = "Colony " + h
                    + String.format("\nHome of: %5d\n", antsPerColony[h])
                    + String.format("Sugar:   %5d\n", nodes[k].sugar());
        } else if (nodes[k].sugar() > 0) {
            text += String.format("Sugar:   %5d\n", nodes[k].sugar());
        }
        if (antsPerNode[k] > 0) {
            text += String.format("Ants:    %5d\n", antsPerNode[k]);
        }
        String[] lines = text.split("\n");
        x += nodePadding;
        y += nodePadding + m.getAscent();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x, y + m.getHeight() * i);
        }
    }

    private void updateAntCounts() {
        Arrays.fill(antsPerNode, 0);
        Arrays.fill(antsPerColony, 0);
        for (Ant a : ants) {
            if (a != null) {
                for (int i = 0; i < nodes.length; i++) {
                    if (nodes[i] == a.current()) {
                        antsPerNode[i] += 1;
                    }
                }
                for (int i = 0; i < colonies.length; i++) {
                    if (colonies[i] == a.home()) {
                        antsPerColony[i] += 1;
                    }
                }
            }
        }
    }

    private String status() {
        updateAntCounts();
        StringBuilder sb = new StringBuilder();
        int aliveAnts = 0;
        for (int i = 0; i < colonies.length; i++) {
            aliveAnts += antsPerColony[i];
        }
        sb.append("Ants")
                .append("\n\talive: ").append(aliveAnts)
                .append("\n\tdead:  ").append(ants.length - aliveAnts);
        sb.append("\n\nColonies");
        for (int i = 0; i < colonies.length; i++) {
            sb.append("\nColony ")
                    .append(i).append(" #")
                    .append(colonies[i].hashCode())
                    .append("\n\tstored sugar: ")
                    .append(colonies[i].sugar())
                    .append("\n\talive ants: ")
                    .append(antsPerColony[i]);
        }
        sb.append("\n\nNodes\n");
        for (int i = 0; i < nodes.length; i++) {
            sb.append("\nNode #").append(nodes[i].hashCode())
                    .append("\n\tsugar: ")
                    .append(nodes[i].sugar())
                    .append("\n\tants: ")
                    .append(antsPerNode[i])
                    .append("\n\tcolony: ")
                    .append((nodes[i] instanceof Colony) ? "yes" : "no");
        }
        return sb.toString();
    }

    /**
     * If the graph is a grid, export the current visualisation of the grid as an image in PNG format.
     *
     * @param filename the path and name of the file without extension
     * @throws IOException
     */
    public void exportGridVisual(String filename) throws IOException {
        if (isGrid) {
            BufferedImage bi = new BufferedImage(grid.getSize().width, grid.getSize().height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            grid.paint(g);
            g.dispose();
            ImageIO.write(bi, "png", new File(filename + ".png"));
        }
    }

    /**
     * Display a window showing a status of the simulation.
     */
    public void display() {
        update();
        window.setVisible(true);
    }

    /**
     * Update the status window.
     */
    public void update() {
        if (isGrid) {
            grid.repaint();
        }
        status.setText(status());
        try {
            Thread.sleep(TICK_SLEEP);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Print a status of the simulation on the standard output.
     */
    public void printStatus() {
        System.out.println(status());
    }

}
