package GUI;

import FindingEntity.Entity;
import FindingEntity.EntityFinder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EntityFind {
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
}
