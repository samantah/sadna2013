/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author fistuk
 */
public class SubForumPage extends javax.swing.JFrame {

    /**
     * Creates new form SubForumPage
     */
    public SubForumPage() {
        initComponents();
        this.setResizable(false);
        String subForumName = CurrentStatus.currSubForum.getSubForumName();
        jLabelTitle.setText("Welcome To The Sub-Forum: " + subForumName);
        DefaultListModel listModel = new DefaultListModel();

        List<ThreadMessage> listOfThreads;
        listOfThreads = CurrentStatus.currUser.viewThreadMessages("forum1", "subForum1");
        for (ThreadMessage tm : listOfThreads) {
            listModel.addElement(tm.getTitle());
        }
        jListThreads.setModel(listModel);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListThreads = new javax.swing.JList();
        jButtonEnterThread = new javax.swing.JButton();
        logInButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitle.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabelTitle.setText("Welcome to...");

        jListThreads.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "List of Threads", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListThreads);

        jButtonEnterThread.setText("Enter Thread");
        jButtonEnterThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnterThreadActionPerformed(evt);
            }
        });

        logInButton.setText("log in");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });

        registerButton.setText("register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

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
                .addContainerGap(113, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonBack)
                        .addGap(18, 18, 18)
                        .addComponent(logInButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(registerButton)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEnterThread)
                        .addGap(202, 202, 202))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEnterThread)
                    .addComponent(registerButton)
                    .addComponent(logInButton)
                    .addComponent(jButtonBack))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        CurrentStatus.currSubForum = null;
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        RegistrationPage registrationPage = new RegistrationPage();
        this.setVisible(false);
        this.dispose();
        registrationPage.setVisible(true);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        LogInPage logInPage = new LogInPage();
        this.setVisible(false);
        this.dispose();
        logInPage.setVisible(true);
    }//GEN-LAST:event_logInButtonActionPerformed

    private void jButtonEnterThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterThreadActionPerformed
        int selectedIndex = jListThreads.getSelectedIndex();
        if (selectedIndex == 0) {
            return;
        }
        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = CurrentStatus.currSubForum.getSubForumName();
        ThreadMessage thread = CurrentStatus.currUser.getThread(forumName, 
                                                   subForumName, selectedIndex);
        CurrentStatus.currThread = thread;
        ThreadPage threadPage = new ThreadPage();
        this.setVisible(false);
        this.dispose();
        threadPage.setVisible(true);
    }//GEN-LAST:event_jButtonEnterThreadActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonEnterThread;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList jListThreads;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logInButton;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
