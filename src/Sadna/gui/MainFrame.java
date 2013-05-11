/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.Admin;
import Sadna.Client.ConnectionHandler;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.db.Forum;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author fistuk
 */
public class MainFrame extends javax.swing.JFrame {

    String host = "192.168.1.105";
    int port = 3333;

    public MainFrame() {
        initComponents();
        this.setResizable(false);
        ClientCommunicationHandlerInterface ch = new ConnectionHandler(host, port);
//        CurrentStatus.currUser = new Admin("chen", "chen1234", "", "newForum", ch);
        CurrentStatus.currUser = new User(ch);
        List<Forum> viewForums = CurrentStatus.currUser.viewForums();
        DefaultListModel listOfForums = new DefaultListModel();
        for (Forum f : viewForums) {
            listOfForums.addElement(f.getForumName());
        }
        forumsList.setModel(listOfForums);

        if (!(CurrentStatus.currUser instanceof SuperAdmin)) {
            jButtonInitiateForum.setVisible(false);
            jLabelNumberOfForums.setVisible(false);
            jLabelNumberOfMembersInForum.setVisible(false);
            String text = jLabelNumberOfForums.getText();
            jLabelNumberOfForums.setText(text + " " + String.valueOf(viewForums.size()));
        }

        
        
        this.forumsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 1) {
                    
                } 
            }
        });
    }
                
    
        
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logInButton = new javax.swing.JButton();
        headLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        forumsList = new javax.swing.JList();
        enterForumButton = new javax.swing.JButton();
        jButtonInitiateForum = new javax.swing.JButton();
        jLabelNumberOfForums = new javax.swing.JLabel();
        jLabelNumberOfMembersInForum = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logInButton.setText("log in as admin");
        logInButton.setToolTipText("log in as super admin");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });

        headLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headLabel.setText("Welcome to our forum");

        forumsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of forums", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        forumsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickHandler(evt);
            }
        });
        jScrollPane1.setViewportView(forumsList);

        enterForumButton.setText("Enter Forum");
        enterForumButton.setToolTipText("enters the selected forum from the list above");
        enterForumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterForumButtonActionPerformed(evt);
            }
        });

        jButtonInitiateForum.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jButtonInitiateForum.setText("initiate new forum");
        jButtonInitiateForum.setToolTipText("an option to initiate a new forum");

        jLabelNumberOfForums.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelNumberOfForums.setText("Number of forums: ");

        jLabelNumberOfMembersInForum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelNumberOfMembersInForum.setText("Number of members in forum: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(logInButton)
                                .addGap(45, 45, 45)
                                .addComponent(enterForumButton)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(jLabelNumberOfMembersInForum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabelNumberOfMembersInForum, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(logInButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enterForumButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enterForumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterForumButtonActionPerformed
        String selectedValue = (String) forumsList.getSelectedValue();
        if (selectedValue == null) {
            return;
        }
        Forum forum = CurrentStatus.currUser.getForum(selectedValue);
        CurrentStatus.currForum = forum;
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);
    }//GEN-LAST:event_enterForumButtonActionPerformed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        LogInAsAdminPage logInAsAdminPage = new LogInAsAdminPage();
        this.setVisible(false);
        this.dispose();
        logInAsAdminPage.setVisible(true);
    }//GEN-LAST:event_logInButtonActionPerformed

    private void clickHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickHandler
        int clickCount = evt.getClickCount();
        if (clickCount==1){
            String text = "";
            text = "Number of members in forum: ";
            Forum selectedForum = (Forum) this.forumsList.getSelectedValue();
            SuperAdmin admin = (SuperAdmin)CurrentStatus.currUser;
            this.jLabelNumberOfMembersInForum.setText(text);
        }
    }//GEN-LAST:event_clickHandler

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton enterForumButton;
    private javax.swing.JList forumsList;
    private javax.swing.JLabel headLabel;
    private javax.swing.JButton jButtonInitiateForum;
    private javax.swing.JLabel jLabelNumberOfForums;
    private javax.swing.JLabel jLabelNumberOfMembersInForum;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logInButton;
    // End of variables declaration//GEN-END:variables
}
