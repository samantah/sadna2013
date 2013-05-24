/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.Member;



/**
 *
 * @author fistuk
 */
public class LogInPage extends ForumJFrame {

    /**
     * Creates new form LogInPage
     */
    public LogInPage() {
        initComponents();
        this.setResizable(false);
        jLabelInvalidData.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelUserName = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jButtonLogin = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabelInvalidData = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelUserName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelUserName.setText("UserName ");

        jLabelPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelPassword.setText("Password");

        jButtonLogin.setText("log in");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jLabelTitle.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabelTitle.setText("Please log in");

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jLabelInvalidData.setForeground(new java.awt.Color(255, 0, 0));
        jLabelInvalidData.setText("Invalid data - Username or Password are incorrect");

        jButtonBack.setText("back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelInvalidData, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUserName)
                            .addComponent(jLabelPassword)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButtonBack)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(jPasswordField1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButtonLogin)
                                .addGap(21, 21, 21)))))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUserName)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLogin)
                    .addComponent(jButtonBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelInvalidData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        goBack();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        String userNameStr = jTextFieldUserName.getText();
        String passwordStr = jPasswordField1.getText();
        String forumName = CurrentStatus.currForum.getForumName();
        Member login = CurrentStatus.currUser.login(forumName, userNameStr, passwordStr);
        if (login==null){
            jLabelInvalidData.setVisible(true);
            jTextFieldUserName.setText("");
            jPasswordField1.setText("");
            return;
        }
        CurrentStatus.currUser = login;
        goBack();
        
    }//GEN-LAST:event_jButtonLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelInvalidData;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUserName;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextFieldUserName;
    // End of variables declaration//GEN-END:variables

    private void goBack() {
        EnumPages whereToGo = null;
        ForumJFrame frame = null;
        for (int i = 0; i < 1; i++) {
            if (CurrentStatus.currForum == null) {
                whereToGo = EnumPages.MAIN;
                break;
            }
            if (CurrentStatus.currSubForum == null) {
                whereToGo = EnumPages.FORUM;
                break;
            }
            if (CurrentStatus.currThread == null) {
                whereToGo = EnumPages.SUBFORUM;
                break;
            }
            whereToGo = EnumPages.THREAD;
        }
        switch (whereToGo) {
            case MAIN:
                frame = new MainFrame();
                break;
            case FORUM:
                frame = new ForumPage();
                break;
            case SUBFORUM:
                frame = new SubForumPage();
                break;
            case THREAD:
                frame = new ThreadPage();
                break;

        }
        this.setVisible(false);
        this.dispose();
        frame.setVisible(true);
        CurrentStatus.currFrame = frame;
    }
}
