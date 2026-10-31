import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentGUI extends JFrame {

    private StudentBST bst = new StudentBST();

    private JTextField txtId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtMajor;
    private JComboBox<String> cmbYear;

    private JTable table;
    private DefaultTableModel model;

    private final Color PURPLE = new Color(112, 79, 160);
    private final Color LIGHT_PURPLE = new Color(243, 237, 251);
    private final Color YELLOW = new Color(255, 243, 194);
    private final Color DARK = new Color(55, 48, 65);

    public StudentGUI() {
        setTitle("Student Information Management System");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(850, 600));

        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(LIGHT_PURPLE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PURPLE);
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = new JLabel("Student Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Binary Search Tree | Student Information"
        );
        subtitle.setForeground(new Color(235, 225, 250));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 3, 3));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Main content
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(LIGHT_PURPLE);
        main.setBorder(
                BorderFactory.createEmptyBorder(0, 20, 20, 20)
        );

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 216, 239)
                        ),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtFirstName = new JTextField(15);
        txtLastName = new JTextField(15);
        txtMajor = new JTextField(15);

        cmbYear = new JComboBox<>(
                new String[]{"1", "2", "3", "4", "5", "6"}
        );

        addField(form, gbc, 0, 0, "Student ID", txtId);
        addField(form, gbc, 0, 1, "First Name", txtFirstName);
        addField(form, gbc, 0, 2, "Last Name", txtLastName);
        addField(form, gbc, 2, 0, "Major", txtMajor);
        addField(form, gbc, 2, 1, "Year", cmbYear);

        // Buttons
        JButton btnAdd = createButton("Add Student", PURPLE);
        JButton btnSearch = createButton("Search", PURPLE);
        JButton btnUpdate = createButton("Update", PURPLE);

        JButton btnDelete = createButton(
                "Delete", new Color(190, 75, 90)
        );

        JButton btnClear = createButton(
                "Clear", new Color(120, 110, 135)
        );

        JButton btnTree = createButton(
                "View BST", new Color(185, 145, 55)
        );

        // Display All button
        JButton btnDisplay = createButton(
                "Display All", new Color(95, 145, 125)
        );

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 8)
        );

        buttons.setBackground(Color.WHITE);

        buttons.add(btnAdd);
        buttons.add(btnSearch);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnTree);
        buttons.add(btnDisplay);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(12, 5, 5, 5);

        form.add(buttons, gbc);

        // Table
        String[] columns = {
                "Student ID",
                "First Name",
                "Last Name",
                "Major",
                "Year"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        table.getTableHeader().setBackground(YELLOW);
        table.getTableHeader().setForeground(DARK);
        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        JScrollPane scroll = new JScrollPane(table);

        scroll.setBorder(
                BorderFactory.createTitledBorder("Student Records")
        );

        main.add(form, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addStudent());

        btnSearch.addActionListener(e -> searchStudent());

        btnUpdate.addActionListener(e -> updateStudent());

        btnDelete.addActionListener(e -> deleteStudent());

        btnClear.addActionListener(e -> clearForm());

        btnTree.addActionListener(e -> showTree());

        // Display All action
        btnDisplay.addActionListener(e -> {
            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "All student records displayed!"
            );
        });

        // Select row to fill form
        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && table.getSelectedRow() != -1) {

                int row = table.getSelectedRow();

                txtId.setText(
                        model.getValueAt(row, 0).toString()
                );

                txtFirstName.setText(
                        model.getValueAt(row, 1).toString()
                );

                txtLastName.setText(
                        model.getValueAt(row, 2).toString()
                );

                txtMajor.setText(
                        model.getValueAt(row, 3).toString()
                );

                cmbYear.setSelectedItem(
                        model.getValueAt(row, 4).toString()
                );

                txtId.setEditable(false);
            }
        });
    }

    // Create form fields
    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int x,
            int y,
            String label,
            JComponent input
    ) {

        gbc.gridwidth = 1;
        gbc.gridx = x;
        gbc.gridy = y;

        JLabel lbl = new JLabel(label);
        lbl.setForeground(DARK);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));

        panel.add(lbl, gbc);

        gbc.gridx = x + 1;
        panel.add(input, gbc);
    }

    // Create buttons
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setPreferredSize(new Dimension(115, 35));

        return button;
    }

    // Read data from form
    private Student readForm() {

        String id = txtId.getText().trim();
        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();
        String major = txtMajor.getText().trim();

        if (id.isEmpty() || first.isEmpty()
                || last.isEmpty() || major.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );

            return null;
        }

        if (!id.matches("[0-9]+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must contain numbers only."
            );

            return null;
        }

        int year = Integer.parseInt(
                cmbYear.getSelectedItem().toString()
        );

        return new Student(
                id, first, last, major, year
        );
    }

    // Add student
    private void addStudent() {

        Student student = readForm();

        if (student == null) {
            return;
        }

        if (bst.insert(student)) {

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!"
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "This Student ID already exists!"
            );
        }
    }

    // Search student
    private void searchStudent() {

        String id = txtId.getText().trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student ID."
            );

            return;
        }

        Student student = bst.search(id);

        if (student != null) {

            txtFirstName.setText(student.getFirstName());
            txtLastName.setText(student.getLastName());
            txtMajor.setText(student.getMajor());

            cmbYear.setSelectedItem(
                    String.valueOf(student.getYear())
            );

            txtId.setEditable(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Student found!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found!"
            );
        }
    }

    // Update student
    private void updateStudent() {

        String id = txtId.getText().trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student first."
            );

            return;
        }

        Student student = readForm();

        if (student == null) {
            return;
        }

        boolean updated = bst.update(
                id,
                student.getFirstName(),
                student.getLastName(),
                student.getMajor(),
                student.getYear()
        );

        if (updated) {

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Student updated successfully!"
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found!"
            );
        }
    }

    // Delete student
    private void deleteStudent() {

        String id = txtId.getText().trim();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student ID."
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            if (bst.delete(id)) {

                refreshTable();
                clearForm();

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student not found!"
                );
            }
        }
    }

    // Refresh table using Inorder Traversal
    private void refreshTable() {

        model.setRowCount(0);

        for (Student s : bst.getAllStudents()) {

            model.addRow(new Object[]{
                    s.getId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getMajor(),
                    s.getYear()
            });
        }
    }

    // Clear form
    private void clearForm() {

        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtMajor.setText("");

        cmbYear.setSelectedIndex(0);

        txtId.setEditable(true);

        table.clearSelection();
    }

    // Open BST window
    private void showTree() {

        JDialog dialog = new JDialog(
                this,
                "Binary Search Tree",
                true
        );

        dialog.setSize(850, 600);
        dialog.setLocationRelativeTo(this);

        TreePanel treePanel = new TreePanel(
                bst.getRoot()
        );

        JScrollPane scroll = new JScrollPane(treePanel);

        dialog.add(scroll);

        dialog.setVisible(true);
    }
}