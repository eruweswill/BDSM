import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        setTitle("Home");
        setSize(1600, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        JPanel boxContainer = new JPanel();
        boxContainer.setLayout(new GridLayout(1, 3, 50, 0));
        boxContainer.setOpaque(false);

        RoundedPanel boxLinks = new RoundedPanel();
        RoundedPanel boxTime = new RoundedPanel();
        RoundedPanel boxEmergency = new RoundedPanel();

        JLabel link_label = new JLabel("Link");
        link_label.setFont(new Font("Serif", Font.BOLD, 16));
        JLabel timer_label = new JLabel("Timer (Numbers Only)");
        timer_label.setFont(new Font("Serif", Font.BOLD, 16));
        JLabel emergency_label = new JLabel("Emergency access");
        emergency_label.setFont(new Font("Serif", Font.BOLD, 16));

        boxLinks.add(link_label);
        boxTime.add(timer_label);
        boxEmergency.add(emergency_label);

        // Initial fields
        JTextField textFieldLinks = createTextField(boxLinks, boxTime, boxEmergency);
        boxLinks.add(textFieldLinks);

        // Create an editable timer field that only accepts numbers
        JTextField textFieldTime = createNumericTextField();
        boxTime.add(textFieldTime);

        // Initial emergency switch
        JToggleButton emergencySwitch = new JToggleButton("x");
        emergencySwitch.setSize(40,40);
        boxEmergency.add(emergencySwitch);

        boxContainer.add(boxLinks);
        boxContainer.add(boxTime);
        boxContainer.add(boxEmergency);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 20, 20);

        panel.add(boxContainer, gbc);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createTextField(RoundedPanel boxLinks, RoundedPanel boxTime, RoundedPanel boxEmergency) {
        JTextField textField = new JTextField(24);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check for existing empty text fields
                boolean hasEmptyField = false;

                for (Component component : boxLinks.getComponents()) {
                    if (component instanceof JTextField && component.isVisible() && ((JTextField) component).getText().isEmpty()) {
                        hasEmptyField = true;
                        break;
                    }
                }

                // Create new fields only if no empty fields exist
                if (!hasEmptyField) {
                    // Create a new text field for links
                    JTextField newTextFieldLinks = createTextField(boxLinks, boxTime, boxEmergency);
                    boxLinks.add(newTextFieldLinks);
                    boxLinks.revalidate();
                    boxLinks.repaint();

                    // Create a new editable text field for the timer
                    JTextField newTextFieldTime = createNumericTextField();
                    boxTime.add(newTextFieldTime);
                    boxTime.revalidate();
                    boxTime.repaint();

                    // Create a new emergency switch
                    JToggleButton newEmergencySwitch = new JToggleButton("x");
                    newEmergencySwitch.setSize(40,40);
                    boxEmergency.add(newEmergencySwitch);
                    boxEmergency.revalidate();
                    boxEmergency.repaint();

                    // Focus on the newly created link text field
                    newTextFieldLinks.requestFocusInWindow();
                } else {
                    // If there's an empty field, focus on it
                    for (Component component : boxLinks.getComponents()) {
                        if (component instanceof JTextField && ((JTextField) component).getText().isEmpty()) {
                            component.requestFocusInWindow();
                            break;
                        }
                    }
                }
            }
        });
        return textField;
    }

    private JTextField createNumericTextField() {
        JTextField numericTextField = new JTextField(24);

        ((AbstractDocument) numericTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d*")) { //only digits
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;
                if (text.matches("\\d*")) { //only digits
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
            }
        });

        return numericTextField;
    }

    class RoundedPanel extends JPanel {
        private int cornerRound = 18;

        public RoundedPanel() {
            setOpaque(false);
            setBackground(Color.LIGHT_GRAY);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g.create();

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(getBackground());
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRound, cornerRound);
            g2D.dispose();
        }
    }

    public static void main(String[] args) {
        new HomeScreen();
    }
}
