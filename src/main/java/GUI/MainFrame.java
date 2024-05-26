package GUI;

import Search.*;

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
    static private Utilities ultility = new Utilities();
    static private TopTrending trend = new TopTrending();
    static private EntityFind entity = new EntityFind();

    public MainFrame() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(255,255,255));         
        TextForSearch.setBackground(new java.awt.Color(235,235,235));
        TextForSearch.setForeground(new java.awt.Color(0,0,0));
        
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Content.setLayout(new BoxLayout(Content, BoxLayout.Y_AXIS));
        Object.setLayout(new BoxLayout(Object, BoxLayout.Y_AXIS));
        Trending.setLayout(new GridLayout(4,1));
        trend.TopTrending();
        startup();
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
        ultility.clearPanel(MainFrame.Content);
        ultility.clearPanel(MainFrame.Object);        
        ContentPanel(listNews);
        ultility.updateScrollPane(MainFrame.MainPanel, MainFrame.ScrollPane);
        ultility.updatePanel(MainFrame.Content);
    }
    // đưa ra xâu chỉ id bài viết chứa ký tự nhập vào
    public static ArrayList<News> searchSuggestion(String search) throws MalformedURLException, IOException, ParseException, org.json.simple.parser.ParseException {
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
    // show ra các bài viết
    public static void ContentPanel(ArrayList<News> listNew){
        if (listNew != null && !listNew.isEmpty()){
            for (News news : listNew) {
            Article article = new Article();
            Content.add(article.ArticlePanel(news));
            }}
        else{
                ultility.clearPanel(MainFrame.Content);
                JLabel Nothing = new JLabel("           There are no articles to search for!");
                Nothing.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font Arial, kích thước 20   
                Content.add(Nothing);
                ultility.updatePanel(MainFrame.Content);
        }
    }
    // phần xử lý khi thao tác Enter hoặc là bấm Button Search
    private void solve() {
    String search = TextForSearch.getText();
    if (search == null || search.trim().isEmpty()) {
        return; // Không làm gì nếu xâu nhập vào là rỗng hoặc null
    }
    // Xoá màn hình
    ultility.clearPanel(MainFrame.Content);
    ultility.clearPanel(MainFrame.Object); 
    try {
        search = search.trim(); // Loại bỏ khoảng trắng thừa
        ArrayList<News> ans = searchSuggestion(search);
        ContentPanel(ans);
        entity.EntityFind(search);
    } catch (Exception e) {
        e.printStackTrace();
    }   
    ultility.updateScrollPane(MainFrame.MainPanel, MainFrame.ScrollPane);
    ultility.updatePanel(MainFrame.Content);
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
        startup();
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
    public static javax.swing.JPanel MainPanel;
    public static javax.swing.JPanel Object;
    public static javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton Search;
    private javax.swing.JTextField TextForSearch;
    public static javax.swing.JPanel Trending;
    // End of variables declaration//GEN-END:variables
}
