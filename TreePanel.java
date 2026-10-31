import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {

    private TreeNode root;

    private final int NODE_WIDTH = 85;
    private final int NODE_HEIGHT = 45;
    private final int HORIZONTAL_GAP = 110;
    private final int VERTICAL_GAP = 100;

    private Map<TreeNode, Point> positions;

    public TreePanel(TreeNode root) {
        this.root = root;
        this.positions = new HashMap<>();

        setBackground(Color.WHITE);

        int count = countNodes(root);
        int depth = getDepth(root);

        int width = Math.max(850, count * HORIZONTAL_GAP + 100);
        int height = Math.max(300, depth * VERTICAL_GAP + 120);

        setPreferredSize(new Dimension(width, height));
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;

        return 1 + countNodes(node.left)
                + countNodes(node.right);
    }

    private int getDepth(TreeNode node) {
        if (node == null) return 0;

        return 1 + Math.max(
                getDepth(node.left),
                getDepth(node.right)
        );
    }

    private void calculatePositions() {
        positions.clear();

        int[] index = {0};

        assignPosition(root, 0, index);
    }

    private void assignPosition(TreeNode node,
                                int depth, int[] index) {

        if (node == null) return;

        assignPosition(node.left, depth + 1, index);

        int x = 60 + index[0] * HORIZONTAL_GAP;
        int y = 50 + depth * VERTICAL_GAP;

        positions.put(node, new Point(x, y));

        index[0]++;

        assignPosition(node.right, depth + 1, index);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (root == null) {
            g2.setColor(Color.GRAY);
            g2.drawString(
                    "No student data available.",
                    30, 50
            );

            g2.dispose();
            return;
        }

        calculatePositions();

        // Draw connecting lines
        drawLines(g2, root);

        // Draw nodes
        for (Map.Entry<TreeNode, Point> entry
                : positions.entrySet()) {

            drawNode(
                    g2,
                    entry.getKey(),
                    entry.getValue()
            );
        }

        g2.dispose();
    }

    private void drawLines(Graphics2D g2, TreeNode node) {
        if (node == null) return;

        Point parent = positions.get(node);

        if (node.left != null) {
            Point child = positions.get(node.left);

            g2.setColor(new Color(170, 145, 205));
            g2.setStroke(new BasicStroke(2));

            g2.drawLine(
                    parent.x + NODE_WIDTH / 2,
                    parent.y + NODE_HEIGHT,
                    child.x + NODE_WIDTH / 2,
                    child.y
            );

            drawLines(g2, node.left);
        }

        if (node.right != null) {
            Point child = positions.get(node.right);

            g2.setColor(new Color(170, 145, 205));
            g2.setStroke(new BasicStroke(2));

            g2.drawLine(
                    parent.x + NODE_WIDTH / 2,
                    parent.y + NODE_HEIGHT,
                    child.x + NODE_WIDTH / 2,
                    child.y
            );

            drawLines(g2, node.right);
        }
    }

    private void drawNode(Graphics2D g2,
                          TreeNode node, Point point) {

        g2.setColor(new Color(239, 230, 251));

        g2.fillRoundRect(
                point.x, point.y,
                NODE_WIDTH, NODE_HEIGHT,
                15, 15
        );

        g2.setColor(new Color(112, 79, 160));
        g2.setStroke(new BasicStroke(2));

        g2.drawRoundRect(
                point.x, point.y,
                NODE_WIDTH, NODE_HEIGHT,
                15, 15
        );

        String id = node.data.getId();

        FontMetrics fm = g2.getFontMetrics();

        int textX = point.x
                + (NODE_WIDTH - fm.stringWidth(id)) / 2;

        int textY = point.y
                + (NODE_HEIGHT + fm.getAscent()) / 2 - 3;

        g2.setColor(new Color(75, 49, 115));
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));

        g2.drawString(id, textX, textY);
    }
}