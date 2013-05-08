/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Server.Users.Member;
import Sadna.Server.Users.User;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author fistuk
 */
public class ThreadPage extends javax.swing.JFrame {

    /**
     * Creates new form ThreadPage
     */
    public ThreadPage() {
        initComponents();
        this.setResizable(false);
        this.jLabelError.setVisible(false);

        if (CurrentStatus.currUser instanceof Member) {
            logInButton.setVisible(false);
            registerButton.setVisible(false);
        } else {
            jButtonSignout.setVisible(false);
            jTextFieldAddTitle.setVisible(false);
            jTextFieldAddContent.setVisible(false);
            jButtonAddPost.setVisible(false);
        }

        this.setResizable(false);
        jListPosts.setCellRenderer(new MyCellRenderer(80));
        jListPosts.setFixedCellWidth(80);
        jLabelThreadTitle.setText("Title: " + CurrentStatus.currThread.getTitle());
        jLabelThreadContent.setText("Content: " + CurrentStatus.currThread.getContent());

        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = CurrentStatus.currSubForum.getSubForumName();
        int threadId = CurrentStatus.currThread.getId();

        DefaultListModel listModel = new DefaultListModel();
        List<Post> listOfPosts;
        ThreadMessage thread = CurrentStatus.currUser.getThread(forumName,
                subForumName, threadId);
        listOfPosts = CurrentStatus.currUser.getAllPosts(thread);
        for (Post p : listOfPosts) {
            listModel.addElement(p);
        }
        jListPosts.setModel(listModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonBack = new javax.swing.JButton();
        logInButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jLabelThreadTitle = new javax.swing.JLabel();
        jLabelThreadContent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListPosts = new javax.swing.JList();
        jButtonAddPost = new javax.swing.JButton();
        jTextFieldAddTitle = new javax.swing.JTextField();
        jTextFieldAddContent = new javax.swing.JTextField();
        jButtonSignout = new javax.swing.JButton();
        jLabelError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonBack.setText("back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
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

        jLabelThreadTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelThreadTitle.setText("Thread title");

        jLabelThreadContent.setText("Thread content");

        jListPosts.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of posts", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListPosts);

        jButtonAddPost.setText("add post");
        jButtonAddPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddPostActionPerformed(evt);
            }
        });

        jButtonSignout.setText("sign out");
        jButtonSignout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignoutActionPerformed(evt);
            }
        });

        jLabelError.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError.setText("Error - didn't post");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSignout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logInButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(registerButton))
                    .addComponent(jLabelThreadContent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addComponent(jLabelThreadTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAddContent, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                            .addComponent(jTextFieldAddTitle))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAddPost)
                        .addGap(113, 113, 113))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelError, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabelThreadTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelThreadContent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldAddTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldAddContent))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabelError)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack)
                    .addComponent(registerButton)
                    .addComponent(logInButton)
                    .addComponent(jButtonAddPost)
                    .addComponent(jButtonSignout))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        CurrentStatus.currThread = null;
        SubForumPage subForumPage = new SubForumPage();
        this.setVisible(false);
        this.dispose();
        subForumPage.setVisible(true);
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        LogInPage logInPage = new LogInPage();
        this.setVisible(false);
        this.dispose();
        logInPage.setVisible(true);
    }//GEN-LAST:event_logInButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        RegistrationPage registrationPage = new RegistrationPage();
        this.setVisible(false);
        this.dispose();
        registrationPage.setVisible(true);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void jButtonSignoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignoutActionPerformed
        Member m = (Member) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String userName = m.getUserName();
        User logout = m.logout(forumName, userName);
        CurrentStatus.currUser = logout;
        ThreadPage threadPage = new ThreadPage();
        this.setVisible(false);
        this.dispose();
        threadPage.setVisible(true);
    }//GEN-LAST:event_jButtonSignoutActionPerformed

    private void jButtonAddPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPostActionPerformed
        String title = jTextFieldAddTitle.getText();
        String content = jTextFieldAddContent.getText();
        Member m = (Member) CurrentStatus.currUser;
        ThreadMessage tm = CurrentStatus.currThread;
        Post p = new Post(tm, title, content, m.getUserName());
        boolean postComment = m.postComment(p);
        if (!postComment){
            this.jLabelError.setVisible(true);
            return;
        }
        ThreadPage threadPage = new ThreadPage();
        this.setVisible(false);
        this.dispose();
        threadPage.setVisible(true);
    }//GEN-LAST:event_jButtonAddPostActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddPost;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonSignout;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JLabel jLabelThreadContent;
    private javax.swing.JLabel jLabelThreadTitle;
    private javax.swing.JList jListPosts;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAddContent;
    private javax.swing.JTextField jTextFieldAddTitle;
    private javax.swing.JButton logInButton;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
