import java.util.ArrayList;
import java.util.List;

public class StudentBST {

    private TreeNode root;

    public StudentBST() {
        root = null;
    }

    // เพิ่มข้อมูลนักศึกษา
    public boolean insert(Student student) {
        if (search(student.getId()) != null) {
            return false;
        }

        root = insertNode(root, student);
        return true;
    }

    private TreeNode insertNode(TreeNode node, Student student) {
        if (node == null) {
            return new TreeNode(student);
        }

        int compare = student.getId().compareTo(node.data.getId());

        if (compare < 0) {
            node.left = insertNode(node.left, student);
        } else if (compare > 0) {
            node.right = insertNode(node.right, student);
        }

        return node;
    }

    // ค้นหาข้อมูลนักศึกษา
    public Student search(String id) {
        TreeNode current = root;

        while (current != null) {
            int compare = id.compareTo(current.data.getId());

            if (compare == 0) {
                return current.data;
            } else if (compare < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // แก้ไขข้อมูลนักศึกษา
    public boolean update(String id, String firstName,
                          String lastName, String major, int year) {

        Student student = search(id);

        if (student == null) {
            return false;
        }

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setMajor(major);
        student.setYear(year);

        return true;
    }

    // ลบข้อมูลนักศึกษา
    public boolean delete(String id) {
        if (search(id) == null) {
            return false;
        }

        root = deleteNode(root, id);
        return true;
    }

    private TreeNode deleteNode(TreeNode node, String id) {
        if (node == null) {
            return null;
        }

        int compare = id.compareTo(node.data.getId());

        if (compare < 0) {
            node.left = deleteNode(node.left, id);
        } else if (compare > 0) {
            node.right = deleteNode(node.right, id);
        } else {

            // กรณีไม่มีลูก
            if (node.left == null && node.right == null) {
                return null;
            }

            // กรณีมีลูกด้านเดียว
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            // กรณีมีลูกสองด้าน
            TreeNode successor = findMin(node.right);

            node.data = successor.data;

            node.right = deleteNode(
                    node.right, successor.data.getId()
            );
        }

        return node;
    }

    // หา Node ที่มีค่าน้อยที่สุด
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // แสดงข้อมูลทั้งหมดด้วย Inorder Traversal
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        inorder(root, students);
        return students;
    }

    private void inorder(TreeNode node, List<Student> students) {
        if (node != null) {
            inorder(node.left, students);
            students.add(node.data);
            inorder(node.right, students);
        }
    }

    // ส่ง Root ให้หน้าจอแสดง Tree
    public TreeNode getRoot() {
        return root;
    }

    // นับจำนวน Node
    public int getSize() {
        return countNodes(root);
    }

    private int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNodes(node.left)
                + countNodes(node.right);
    }
}
