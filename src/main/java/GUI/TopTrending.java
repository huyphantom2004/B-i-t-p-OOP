package GUI;

import Search.News;
import Trending.TrendExport;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.json.simple.parser.ParseException;

public class TopTrending {
    
private void TopTrending(){
        TrendExport trendExport = new TrendExport();
        String[] top3Hashtag = trendExport.mostFrequentTag();
     
        JLabel TrendTopic = new JLabel("Trending");
            TrendTopic.setForeground(new Color(0, 0, 0)); 
            TrendTopic.setBackground(new Color(240,248,255));   
            TrendTopic.setOpaque(true);         
        TrendTopic.setHorizontalAlignment(JLabel.CENTER);
        TrendTopic.setVerticalAlignment(JLabel.CENTER);
        TrendTopic.setFont(new Font("Roboto", Font.PLAIN, 18)); // Font Arial, kích thước 20
        Font font = TrendTopic.getFont();        
        TrendTopic.setFont(font.deriveFont(font.getStyle() | Font.BOLD ));
        Dimension preferredSize = new Dimension(298, 30);
        TrendTopic.setPreferredSize(preferredSize);        
        MainFrame.Trending.add(TrendTopic);
        
        for (int i =0;i<3;i++){
            String tag = top3Hashtag[i];
            JLabel Tag = new JLabel("       "+tag);
            Tag.setPreferredSize(preferredSize);  
            Tag.setFont(new Font("Roboto", Font.PLAIN, 16)); // Font Arial, kích thước 20
                Tag.setForeground(new Color(0, 0, 0)); 
                Tag.setBackground(new Color(240,248,255));   
                Tag.setOpaque(true); 
            Tag.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent  e) {
                        if (!tag.equals("null")){                            
                        MainFrame.Content.clearPanel();
                        ArrayList<News> ans;
                        try {
                            ans = searchSuggestion(tag);
                            ContentPanel(ans);
                        } catch (IOException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        updateScrollPane();
                        // Thêm panel mới vào Content và cập nhật hiển thị
                        Content.revalidate();
                        Content.repaint();}
                    }                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Khi chuột vào, đặt font in đậm và có gạch chân
                        Font font = Tag.getFont();
                        Tag.setFont(font.deriveFont(font.getStyle() | Font.BOLD ));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Khi chuột ra, đặt lại font bình thường
                        Font font = Tag.getFont();
                        Tag.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD ));
                    }
                });
            // Thêm JLabel Tag vào container Trending
            Trending.add(Tag);
        }
}
}
