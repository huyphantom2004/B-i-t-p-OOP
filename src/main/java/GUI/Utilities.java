package GUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Utilities {
    public static void updateScrollPane(JPanel containerPanel, JScrollPane scrollPane) {
        int preferredHeight = containerPanel.getPreferredSize().height;
        int scrollPaneHeight = scrollPane.getViewport().getViewSize().height;

        // Kiểm tra nếu kích thước của containerPanel lớn hơn kích thước của JScrollPane
        if (preferredHeight > scrollPaneHeight) {
            // Nếu có, hiển thị thanh cuộn dọc
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            // Nếu không, ẩn thanh cuộn dọc
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
    }
    public static void clearPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    public static void updatePanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }
}
