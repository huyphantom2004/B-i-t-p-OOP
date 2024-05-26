package API;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Utilities {
    private static JPanel containerPanel; // Thêm một JPanel làm container
    private static JScrollPane ScrollPane; // Thêm một JPanel làm container
    public Utilities(JPanel containerPanel) {
        this.containerPanel = containerPanel;
    }
    private static void updateScrollPane() {
        int preferredHeight = containerPanel.getPreferredSize().height; // Thay đổi từ MainPanel thành containerPanel
        int scrollPaneHeight = ScrollPane.getViewport().getViewSize().height;

        // Kiểm tra nếu kích thước của containerPanel lớn hơn kích thước của JScrollPane
        if (preferredHeight > scrollPaneHeight) {
            // Nếu có, hiển thị thanh cuộn dọc
            ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            // Nếu không, ẩn thanh cuộn dọc
            ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
    }
    private static void clearPanel() {
        containerPanel.removeAll(); // Thay đổi từ Content thành containerPanel
        containerPanel.revalidate();
        containerPanel.repaint();
    }
}
