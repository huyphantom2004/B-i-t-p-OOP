package GUI;

import Search.News;
import Trending.TrendExport;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.json.simple.parser.ParseException;

public class TopTrending {
    static private Utilities ultility = new Utilities();
    public void TopTrending() {
        TrendExport trendExport = new TrendExport();
        String[] top3Hashtag = trendExport.mostFrequentTag();

        JLabel TrendTopic = new JLabel("Trending");
        TrendTopic.setForeground(new Color(0, 0, 0));
        TrendTopic.setBackground(new Color(240, 248, 255));
        TrendTopic.setOpaque(true);
        TrendTopic.setHorizontalAlignment(JLabel.CENTER);
        TrendTopic.setVerticalAlignment(JLabel.CENTER);
        TrendTopic.setFont(new Font("Roboto", Font.PLAIN, 18));
        Font font = TrendTopic.getFont();
        TrendTopic.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
        Dimension preferredSize = new Dimension(298, 30);
        TrendTopic.setPreferredSize(preferredSize);
        MainFrame.Trending.add(TrendTopic);

        for (int i = 0; i < 3; i++) {
            String tag = top3Hashtag[i];
            JLabel Tag = new JLabel("       " + tag);
            Tag.setPreferredSize(preferredSize);
            Tag.setFont(new Font("Roboto", Font.PLAIN, 16));
            Tag.setForeground(new Color(0, 0, 0));
            Tag.setBackground(new Color(240, 248, 255));
            Tag.setOpaque(true);
            Tag.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!tag.equals("null")) {
                        ultility.clearPanel(MainFrame.Content);
                        ultility.clearPanel(MainFrame.Object);

                        ArrayList<News> ans;
                        try {
                            ans = MainFrame.searchSuggestion(tag); // Sử dụng instance để gọi phương thức searchSuggestion()
                            MainFrame.ContentPanel(ans);
                        } catch (IOException ex) {
                            Logger.getLogger(TopTrending.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(TopTrending.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ultility.updateScrollPane(MainFrame.MainPanel, MainFrame.ScrollPane);
                        ultility.updatePanel(MainFrame.Content);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Font font = Tag.getFont();
                    Tag.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Font font = Tag.getFont();
                    Tag.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
                }
            });
            MainFrame.Trending.add(Tag);
        }
    }
}
