package GUI;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.net.URISyntaxException;
import java.awt.*;
import java.io.IOException;

import Search.News;

public class DisplayContent {
    public void Display(News news){
        JFrame frame = new JFrame("Information");
        frame.setSize(800, 600);
        frame.setLocation(400, 120); // Đặt vị trí xuất hiện của JFrame
        frame.setBackground(new Color(255,255,255));           

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel();
        titlePanel.setBackground(new Color(255,255,255));           
        title.setBackground(new Color(0, 0, 0, 0)); // Màu nền trong suốt (alpha = 0)

        JScrollPane InforScroll = new JScrollPane();
        JTextPane Infor = new JTextPane();
        Infor.setContentType("text/html"); // Đặt loại nội dung là HTML
        Infor.setMaximumSize(new Dimension(250 ,Integer.MAX_VALUE)); 
        InforScroll.setBackground(new Color(255,255,255));   
        Infor.setBackground(new Color(255,255,255)); 
        
        JTextPane content = new JTextPane();
        JScrollPane contentScroll = new JScrollPane(content);        
        content.setContentType("text/html"); // Đặt loại nội dung là HTML
        content.setMaximumSize(new Dimension(510 ,Integer.MAX_VALUE)); 
        contentScroll.setBackground(new Color(255,255,255));   
        content.setBackground(new Color(240,240,240)); 
        
        titlePanel.setPreferredSize(new Dimension(800, 100));
        // Thiết lập Layout và Constraints
        GroupLayout titlePanelLayout = new GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);

        // Bật tạo ra khoảng cách tự động giữa các thành phần và khoảng cách giữa thành phần và viền của container
        titlePanelLayout.setAutoCreateGaps(true);
        titlePanelLayout.setAutoCreateContainerGaps(true);

        // Thiết lập kích thước ưu tiên cho title (giới hạn kích thước ngang)
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height));
        title.setMaximumSize(new Dimension(600 ,Integer.MAX_VALUE)); 

        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createSequentialGroup()
                .addGap(80) // Khoảng cách 100px từ mép trái của titlePanel đến title
                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER) 
                    .addComponent(title)
                )
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createSequentialGroup()
                .addGroup(titlePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER) 
                    .addComponent(title)
                )
        );

        InforScroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        Infor.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        InforScroll.setPreferredSize(new Dimension(270, 500));
        Infor.setPreferredSize(new Dimension(250, 750));
        
        InforScroll.setViewportView(Infor);       
        Infor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        InforScroll.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        InforScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        InforScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentScroll.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        content.setPreferredSize(new Dimension(510, 750));
        
        contentScroll.setViewportView(content);               
        content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));        
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(InforScroll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contentScroll, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(InforScroll, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                    .addComponent(contentScroll, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        title.setText("<html><body style='text-align: justify; font-family: Roboto; font-size: 18px;'>"+news.getTitle()+ "</body></html>");        
        Infor.setEditable(false);
        Infor.setText("<html><body style='font-family: Roboto; font-size: 12px; text-align: justify;'>" +
                    "<p><b>Author:</b> " + news.getAuthor() + "</p>" +
                    "<p><b>Type:</b> " + news.getTypeBlog() + "</p>" +
                    "<p><b>Category:</b> " + news.getCategory() + "</p>" +
                    "<p><b>Created day:</b> " + news.getCreateDate() + "</p>" +
                    "<p><b>Sumary:</b> " + news.getSummary().replaceAll("\n", "<br>").replaceAll("\u201C", "&ldquo;").replaceAll("\u201D", "&rdquo;").replaceAll("\u2019", "&#x2019;") + "</p>" +
                    "<p><b>Hashtag:</b>" + news.getHashTag().replaceAll("#", " #") + "</span></p>" +                    
                    "<p><b>Website:</b> " + news.getWebsite() + "</p>" +                           
                    "<p><b>Link:</b> <a href='" + news.getLink() + "'>Click here!</a></p></div>" +
                    "</body></html>" );
        Infor.setCaretPosition(0);
        // Thêm HyperlinkListener để xử lý sự kiện khi người dùng nhấp vào liên kết
        Infor.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });        
        content.setEditable(false);                
        content.setText("<html><body style='text-align: justify; font-family: Roboto; font-size: 12px;'>"+ news.getContent().replaceAll("\n", "<br>").replaceAll("\u201C", "&ldquo;").replaceAll("\u201D", "&rdquo;").replaceAll("\u2019", "&#x2019;") + "</body></html>");
        content.setCaretPosition(0);        
}
}
