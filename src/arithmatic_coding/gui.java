package arithmatic_coding;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
public class gui extends JFrame {
    JTextField myText = new JTextField(50);
    JButton compressButton = new JButton("Compress");
    JButton decompressButton = new JButton("Decompress");
gui(){
    setTitle("arithmatic");
    setSize(500, 500);
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(myText);
    getContentPane().add(compressButton);
    getContentPane().add(decompressButton);
    compressButton.addActionListener(new action());
    decompressButton.addActionListener(new action());
    myText.addActionListener(new action());
}
    private class action implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object buttonPressed = e.getSource();
            compression obj = new compression();
            if(buttonPressed.equals(compressButton)){
                String s = myText.getText();
                try {
                    myText.setText(obj.comp(s));
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
            try {


                if (buttonPressed.equals(decompressButton)) {

                    myText.setText(obj.decomp());
                }
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            }
        }

    }

