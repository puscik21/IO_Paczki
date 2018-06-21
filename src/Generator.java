import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class Generator {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton generujMapęButton;
    private JButton generujDaneButton;
    private Dane dane;
    private Mapa_test mapa;

    public Generator() {
        generujMapęButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Mapa_test.generujmape();
                } catch (FileNotFoundException f) {

                }
            }
        });
        generujDaneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dane.run();
            }
        });
    }
}
