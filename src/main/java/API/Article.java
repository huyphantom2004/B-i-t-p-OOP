package API;

import Search.News;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class Article {
    private JPanel Article(ArrayList<News> listNew){
        for (News news : listNew) {
            JPanel ContentPane = new JPanel();
            ContentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            ContentPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75)); // Thiết lập kích thước tối đa cho panel con
            ContentPane.setLayout(new GridLayout(3, 1)); // GridLayout cho 3 thành phần này
            ContentPane.setBackground(new Color(230,230,230));

            // Lấy các giá trị từ ArrayList News
            String a = (String) news.getTitle();
            String b = (String) news.getAuthor();
            String c = (String) news.getCreateDate();

            JLabel Baiviet = new JLabel("    "+a);
            JLabel Tacgia = new JLabel("    Author: "+b);
            JLabel Ngay = new JLabel("    Created Day: "+c);
            Baiviet.setFont(new Font("Roboto", Font.PLAIN, 14)); 
            Tacgia.setFont(new Font("Roboto", Font.PLAIN, 12)); 
            Ngay.setFont(new Font("Roboto", Font.PLAIN, 12));  

            Dimension nameSize = new Dimension(550, 25);
            Baiviet.setPreferredSize(nameSize);        
            Tacgia.setPreferredSize(nameSize);        
            Ngay.setPreferredSize(nameSize);        

            Baiviet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                DisplayContent display = new DisplayContent();
                display.Display(news);
            }                  
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Khi chuột vào, đặt font in đậm và có gạch chân
                    Font font = Baiviet.getFont();
                    Baiviet.setFont(font.deriveFont(font.getStyle() | Font.BOLD ));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    // Khi chuột ra, đặt lại font bình thường
                    Font font = Baiviet.getFont();
                    Baiviet.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD ));
                }
            });
            ContentPane.add(Baiviet);
            ContentPane.add(Tacgia);
            ContentPane.add(Ngay);
            return ContentPane;
            } 
        return null;
    }
}
