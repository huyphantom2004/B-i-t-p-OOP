package GUI;

import Search.*;
import FindingEntity.*;
import Trending.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class MainFrame extends javax.swing.JFrame {
    static private ArrayList<News> listNews =  new ArrayList<>(); /// lưu thông tin các bài viết
    static private ArrayList<HashString> lHash = new ArrayList<HashString>(); // Tìm kiếm ký tự cần tìm trong các bài viết
    
    // set up giao diện mặc định
    public MainFrame() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(255,255,255));         
        TextForSearch.setBackground(new java.awt.Color(235,235,235));
        TextForSearch.setForeground(new java.awt.Color(0,0,0));
        
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Content.setLayout(new BoxLayout(Content, BoxLayout.Y_AXIS));
        Object.setLayout(new BoxLayout(Object, BoxLayout.Y_AXIS));
        Trending.setLayout(new GridLayout(4,1));
        TopTrending();
        updateScrollPane();
        //Khởi tạo toàn bộ bài viết lần đầu tiên
        clearLayout();
        startup();
        updateScrollPane();
        // Thêm panel mới vào Content và cập nhật hiển thị
        Content.revalidate();
        Content.repaint();
     
        // Thêm action cho Enter Key
        Search.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");
        Search.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search.doClick();
            }
        });
    }   
    // khởi tạo thông tin bài viết dưới dạng xâu để tìm kiếm
    private static void setup() {
        FileReader reader = null;
        try {
            /// địa chỉ tương đối này chỉ dùng được với netbeans
            reader = new FileReader("src/main/java/FileStorge/Contents.json");
            
            // tạo một kiểu cho list News
            Gson gson = new Gson();
            java.lang.reflect.Type classOfT = new TypeToken<ArrayList<News>>(){}.getType();

            // Mothod 1: get data in Json with Java
            listNews = gson.fromJson(reader, classOfT);
            
            for(News news : listNews) {
                String res = news.toString();
                HashString cur = new HashString(res.toLowerCase());
                lHash.add(cur);
                lHash.getLast().setHash();
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }
    private static void startup(){
        ContentPanel(listNews);
    }
    // Xóa nội dung hiển thị trước đó
    private static void clearLayout() {
        Content.removeAll();
        Content.revalidate();
        Content.repaint();
    }
    private static void clearObject() {
        Object.removeAll();
        Object.revalidate();
        Object.repaint();
    }
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
        Trending.add(TrendTopic);
        
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
                        clearLayout();
                        clearObject();
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
    private void EntityFind(String text){
    EntityFinder ent = new EntityFinder();
    Entity entity =  ent.FindEntity(text);
    if(entity!=null){        
        ImageIcon icon = new ImageIcon(entity.getImage());
        JLabel imageOut = new JLabel();
        
        JLabel nameOut = new JLabel("   "+entity.getFullName());
        Font fontName = new Font("Roboto", Font.BOLD, 18);
        nameOut.setFont(fontName);
        JLabel descripOut = new JLabel();
        descripOut.setText("<html><div style='text-align:justify;'>" + entity.getDescription().replaceAll("\n", "<br>") + "</div></html>");

        JPanel ImagePanel = new JPanel();
        JPanel TextPanel = new JPanel();                
        imageOut.setHorizontalAlignment(JLabel.CENTER);
        imageOut.setVerticalAlignment(JLabel.CENTER);
        TextPanel.setLayout(new BoxLayout(TextPanel, BoxLayout.Y_AXIS));
        Dimension maxSize = new Dimension(290, 290);
        ImagePanel.setMaximumSize(maxSize);
        
        // Kích thước tối đa của panel
        int maxWidth = 290;
        int maxHeight = 290;
        // Tính toán tỉ lệ scale
        double scale = Math.min((double) maxWidth / icon.getIconWidth(), (double) maxHeight / icon.getIconHeight());

        // Scale ảnh
        int scaledWidth = (int) (icon.getIconWidth() * scale);
        int scaledHeight = (int) (icon.getIconHeight() * scale);
        Image scaledImage = icon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);       
        imageOut.setIcon(icon);
        imageOut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nameOut.setAlignmentX(Component.LEFT_ALIGNMENT);
        descripOut.setAlignmentX(Component.LEFT_ALIGNMENT);
        ImagePanel.add(imageOut);
        TextPanel.add(nameOut);
        TextPanel.add(descripOut); 
        descripOut.setPreferredSize(TextPanel.getSize());
            {
                clearObject();
                Object.setLayout(new GridLayout(2, 1));
                Object.add(ImagePanel);
                Object.add(TextPanel);
            Object.revalidate();
            Object.repaint();            
            }
    }
    else {
        clearObject();
    }
    }
    // đưa ra xâu chỉ id bài viết chứa ký tự nhập vào
    private ArrayList<News> searchSuggestion(String search) throws MalformedURLException, IOException, ParseException, org.json.simple.parser.ParseException {
        ArrayList<News> ans = new ArrayList<News>(); // lưu thông tin vài bài viết chứa ký tự cần tìm
        // search được filter lại tìm cho dễ
        search = search.toLowerCase().replace(" ", "").replace(",", "").replace(".", "").replace(":", "").replace("/", ""); 
        
        // tìm kiếm kết quả
        HashString val = new HashString(search);
        val.setHash();
        int siz2 = val.getStr().length() - 1;

        int dem = 0;
        for(HashString res : lHash)
        {
            News news = listNews.get(dem);
            int siz1 = res.getStr().length() - 1;
            for(int i = 1; i <= siz1 - siz2 + 1; ++i)
            {
                /// kiểm tra xâu val có xuất hiện trong listHash[id] không
                if(res.check(val, i, i + siz2 - 1))
                {
                    ans.add(news);
                    break;
                }
            }
            dem++;
        }   
        return ans;
    }    
    // update thanh lăn chuột
    private void updateScrollPane() {
        int preferredHeight = MainPanel.getPreferredSize().height;
        int scrollPaneHeight = ScrollPane.getViewport().getViewSize().height;

        // Kiểm tra nếu kích thước của MainPanel lớn hơn kích thước của JScrollPane
        if (preferredHeight > scrollPaneHeight) {
            // Nếu có, hiển thị thanh cuộn dọc
            ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            // Nếu không, ẩn thanh cuộn dọc
            ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
    }
    // show ra các bài viết
    private static void ContentPanel(ArrayList<News> listNew){
        if (listNew != null && !listNew.isEmpty()){
            for (News news : listNew) {
            Article article = new Article();
            Content.add(article.ArticlePanel(news));
            }}
        else{
                clearLayout();
                JLabel Nothing = new JLabel("           There are no articles to search for!");
                Nothing.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font Arial, kích thước 20   
                Content.add(Nothing);
                // Thêm panel mới vào Content và cập nhật hiển thị
                Content.revalidate();
                Content.repaint();
        }
    }
    // phần xử lý khi thao tác Enter hoặc là bấm Button Search
    private void solve() {
    String search = TextForSearch.getText();
    if (search == null || search.trim().isEmpty()) {
        return; // Không làm gì nếu xâu nhập vào là rỗng hoặc null
    }
    // Xoá màn hình
    clearLayout();
    clearObject();   
    try {
        search = search.trim(); // Loại bỏ khoảng trắng thừa
        ArrayList<News> ans = searchSuggestion(search);
        ContentPanel(ans);
        EntityFind(search);
    } catch (Exception e) {
        e.printStackTrace();
    }   
    updateScrollPane();   
    // Thêm panel mới vào Content và cập nhật hiển thị
    Content.revalidate();
    Content.repaint();
}   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TextForSearch = new javax.swing.JTextField();
        Home = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        MainPanel = new javax.swing.JPanel();
        Content = new javax.swing.JPanel();
        Trending = new javax.swing.JPanel();
        Object = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search_API");
        setBackground(new java.awt.Color(24, 26, 32));
        setName("MainFrame"); // NOI18N

        TextForSearch.setBackground(new java.awt.Color(230, 230, 230));
        TextForSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(235, 235, 235)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        TextForSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextForSearchActionPerformed(evt);
            }
        });

        Home.setBackground(new java.awt.Color(240, 248, 255));
        Home.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        Home.setText("Home");
        Home.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });

        Search.setBackground(new java.awt.Color(240, 248, 255));
        Search.setFont(new java.awt.Font("Gill Sans MT", 0, 16)); // NOI18N
        Search.setText("Search");
        Search.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));

        Content.setBackground(new java.awt.Color(230, 230, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));

        javax.swing.GroupLayout ContentLayout = new javax.swing.GroupLayout(Content);
        Content.setLayout(ContentLayout);
        ContentLayout.setHorizontalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );
        ContentLayout.setVerticalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );

        Trending.setBackground(new java.awt.Color(240, 248, 255));
        Trending.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout TrendingLayout = new javax.swing.GroupLayout(Trending);
        Trending.setLayout(TrendingLayout);
        TrendingLayout.setHorizontalGroup(
            TrendingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );
        TrendingLayout.setVerticalGroup(
            TrendingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        Object.setBackground(new java.awt.Color(240, 248, 255));
        Object.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout ObjectLayout = new javax.swing.GroupLayout(Object);
        Object.setLayout(ObjectLayout);
        ObjectLayout.setHorizontalGroup(
            ObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ObjectLayout.setVerticalGroup(
            ObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Trending, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Object, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(Trending, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Object, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(Content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        ScrollPane.setViewportView(MainPanel);
        MainPanel.getAccessibleContext().setAccessibleParent(TextForSearch);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextForSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextForSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("MFrame");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // trả về dữ liệu mặc định (trang mặc định)
    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
        clearLayout();
        clearObject();
        startup();
        updateScrollPane();
        // Thêm panel mới vào Content và cập nhật hiển thị
        Content.revalidate();
        Content.repaint();
    }//GEN-LAST:event_HomeActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        solve();
    }//GEN-LAST:event_SearchActionPerformed

    private void TextForSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextForSearchActionPerformed
        solve();
    }//GEN-LAST:event_TextForSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel Content;
    private javax.swing.JButton Home;
    private static javax.swing.JPanel MainPanel;
    public static javax.swing.JPanel Object;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton Search;
    private javax.swing.JTextField TextForSearch;
    public static javax.swing.JPanel Trending;
    // End of variables declaration//GEN-END:variables
}
