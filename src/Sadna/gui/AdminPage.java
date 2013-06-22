/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.db.Forum;
import Sadna.db.Policy;
import Sadna.db.SubForum;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class AdminPage extends ForumJFrame {

    /**
     * Creates new form AdminPage
     */
    public AdminPage() {
        initComponents();
        this.setResizable(false);
        this.jLabelCurrentMessage.setVisible(false);
        jLabelHead.setText("Admin page - Forum: " + CurrentStatus.currForum.getForumName());
        updateListOfSubForums();
        updateListOfMembers();
        Policy policy = CurrentStatus.currForum.getPolicy();
        jLabelPolicies.setText(policy.toString());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelHead = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListSubForums = new javax.swing.JList();
        jLabelSubForumList = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListMembers = new javax.swing.JList();
        jLabelMembersList = new javax.swing.JLabel();
        jButtonAddModerator = new javax.swing.JButton();
        jButtonRemoveModerator = new javax.swing.JButton();
        jButtonDeleteSubForum = new javax.swing.JButton();
        jButtonGetNumberOfThreads = new javax.swing.JButton();
        jLabelNumberOfThreads = new javax.swing.JLabel();
        jButtonNumberOfThreadsForMember = new javax.swing.JButton();
        jLabelNumberOfThreadsForMember = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListFollowersToMember = new javax.swing.JList();
        jButtonListOfMembersForThreads = new javax.swing.JButton();
        jLabelCurrentMessage = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListModerators = new javax.swing.JList();
        jLabelModeratorsList = new javax.swing.JLabel();
        jButtonGetModerators = new javax.swing.JButton();
        jLabelPolicies = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelHead.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N

        jListSubForums.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of sub forums" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jListSubForums);

        jLabelSubForumList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelSubForumList.setText("List of sub forums");

        jListMembers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of members" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListMembers);

        jLabelMembersList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelMembersList.setText("List of members");

        jButtonAddModerator.setText("Add moderator");
        jButtonAddModerator.setToolTipText("Apoints the selected member to be a moderator in the selected sub forum");
        jButtonAddModerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddModeratorActionPerformed(evt);
            }
        });

        jButtonRemoveModerator.setText("Remove moderator");
        jButtonRemoveModerator.setToolTipText("Removes the selected moderator from the selected sub forum");
        jButtonRemoveModerator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveModeratorActionPerformed(evt);
            }
        });

        jButtonDeleteSubForum.setText("Delete Sub-Forum");
        jButtonDeleteSubForum.setToolTipText("Deletes the selected sub forum and all its content");
        jButtonDeleteSubForum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteSubForumActionPerformed(evt);
            }
        });

        jButtonGetNumberOfThreads.setText("Get number of threads");
        jButtonGetNumberOfThreads.setToolTipText("shows the number of threads in this forum");
        jButtonGetNumberOfThreads.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetNumberOfThreadsActionPerformed(evt);
            }
        });

        jLabelNumberOfThreads.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        jButtonNumberOfThreadsForMember.setText("Get number of threads for member");
        jButtonNumberOfThreadsForMember.setToolTipText("shows the number of threads written by the selected member");
        jButtonNumberOfThreadsForMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNumberOfThreadsForMemberActionPerformed(evt);
            }
        });

        jLabelNumberOfThreadsForMember.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        jScrollPane3.setViewportView(jListFollowersToMember);

        jButtonListOfMembersForThreads.setText("Get list of followers for member");
        jButtonListOfMembersForThreads.setToolTipText("shows the number of threads written by the selected member");
        jButtonListOfMembersForThreads.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListOfMembersForThreadsActionPerformed(evt);
            }
        });

        jButtonBack.setText("back");
        jButtonBack.setToolTipText("back to the main page");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jListModerators.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of moderators" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jListModerators);

        jLabelModeratorsList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelModeratorsList.setText("List of moderators");

        jButtonGetModerators.setText("get moderators");
        jButtonGetModerators.setToolTipText("shows the number of threads written by the selected member");
        jButtonGetModerators.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetModeratorsActionPerformed(evt);
            }
        });

        jLabelPolicies.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelPolicies.setText("list of policies");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelSubForumList, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelMembersList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(38, 38, 38)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabelNumberOfThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(jLabelNumberOfThreadsForMember, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButtonNumberOfThreadsForMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButtonGetNumberOfThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelModeratorsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(197, 197, 197))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCurrentMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButtonRemoveModerator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButtonAddModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonDeleteSubForum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(111, 111, 111)
                                        .addComponent(jButtonGetModerators, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jButtonListOfMembersForThreads, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelHead, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPolicies, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabelHead, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPolicies, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelSubForumList, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelMembersList, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelModeratorsList, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jButtonGetNumberOfThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabelNumberOfThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(8, 8, 8)
                                            .addComponent(jButtonNumberOfThreadsForMember, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelNumberOfThreadsForMember, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAddModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonListOfMembersForThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonGetModerators)
                        .addGap(37, 37, 37)))
                .addComponent(jButtonRemoveModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jButtonDeleteSubForum, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCurrentMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBack))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNumberOfThreadsForMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNumberOfThreadsForMemberActionPerformed
        Admin admin = (Admin) CurrentStatus.currUser;
        Member selectedMember = (Member) this.jListMembers.getSelectedValue();
        if (selectedMember == null) {
            return;
        }
        String forumName = CurrentStatus.currForum.getForumName();
        String userName = selectedMember.getUserName();
        String adminUserName = admin.getUserName();
        String adminPassword = admin.getPassword();
        int numOfUserThreads = admin.getNumOfUserThreads(forumName, userName);
        this.jLabelNumberOfThreadsForMember.setText(String.valueOf(numOfUserThreads));
    }//GEN-LAST:event_jButtonNumberOfThreadsForMemberActionPerformed

    private void jButtonGetNumberOfThreadsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetNumberOfThreadsActionPerformed
        Admin admin = (Admin) CurrentStatus.currUser;
        int threadCounter = admin.getThreadCounter(CurrentStatus.currForum.getForumName());
        this.jLabelNumberOfThreads.setText(String.valueOf(threadCounter));
    }//GEN-LAST:event_jButtonGetNumberOfThreadsActionPerformed

    private void jButtonListOfMembersForThreadsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListOfMembersForThreadsActionPerformed
        Admin admin = (Admin) CurrentStatus.currUser;
        Member selectedMember = (Member) this.jListMembers.getSelectedValue();
        if (selectedMember == null) {
            return;
        }
        String userName = selectedMember.getUserName();
        HashMap<String, List<String>> usersPostToUser = admin.getUsersPostToUser(CurrentStatus.currForum.getForumName());
        List<String> listOfFollowers = usersPostToUser.get(userName);
        DefaultListModel listModel = new DefaultListModel();
        for (String string : listOfFollowers) {
            listModel.addElement(string);
        }
        this.jListFollowersToMember.setModel(listModel);
    }//GEN-LAST:event_jButtonListOfMembersForThreadsActionPerformed

    private void jButtonAddModeratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddModeratorActionPerformed
        SubForum selectedSubForum = (SubForum) this.jListSubForums.getSelectedValue();
        Member selectedMember = (Member) this.jListMembers.getSelectedValue();
        Admin admin = (Admin) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = selectedSubForum.getSubForumName();
        String memberUserName = selectedMember.getUserName();
        String userName = admin.getUserName();
        String password = admin.getPassword();
        boolean addModerator = admin.addModerator(forumName, subForumName, memberUserName);
        this.jLabelCurrentMessage.setVisible(true);
        if (addModerator) {
            this.jLabelCurrentMessage.setText(memberUserName + " was added to "
                    + "be a moderator of sub-forum " + subForumName);
        } else {
            this.jLabelCurrentMessage.setText("cannot add " + memberUserName
                    + " as moderator to sub-forum " + subForumName);

        }

    }//GEN-LAST:event_jButtonAddModeratorActionPerformed

    private void jButtonRemoveModeratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveModeratorActionPerformed
        SubForum selectedSubForum = (SubForum) this.jListSubForums.getSelectedValue();
        Member selectedMember = (Member) this.jListModerators.getSelectedValue();
        if (selectedSubForum == null || selectedMember == null) {
            return;
        }
        Admin admin = (Admin) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = selectedSubForum.getSubForumName();
        String memberUserName = selectedMember.getUserName();
        String userName = admin.getUserName();
        String password = admin.getPassword();
        boolean removeModerator = admin.removeModerator(forumName, subForumName, memberUserName);
        this.jLabelCurrentMessage.setVisible(true);
        if (removeModerator) {
            this.jLabelCurrentMessage.setText(memberUserName + " is no longer "
                    + "a moderator of sub-forum " + subForumName);
        } else {
            this.jLabelCurrentMessage.setText("cannot delete " + memberUserName
                    + " as a moderator");
        }
    }//GEN-LAST:event_jButtonRemoveModeratorActionPerformed

    private void jButtonDeleteSubForumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteSubForumActionPerformed
        SubForum selectedSubForum = (SubForum) this.jListSubForums.getSelectedValue();
        Member selectedMember = (Member) this.jListMembers.getSelectedValue();
        Admin admin = (Admin) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = selectedSubForum.getSubForumName();
        admin.deleteSubForum(forumName, subForumName);
        updateListOfSubForums();

    }//GEN-LAST:event_jButtonDeleteSubForumActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);
        CurrentStatus.currFrame = forumPage;
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonGetModeratorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetModeratorsActionPerformed
        SubForum selectedValue = (SubForum) this.jListSubForums.getSelectedValue();
        if (selectedValue==null){
            return;
        }
        updateListOfModerators(selectedValue.getSubForumName());
    }//GEN-LAST:event_jButtonGetModeratorsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddModerator;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDeleteSubForum;
    private javax.swing.JButton jButtonGetModerators;
    private javax.swing.JButton jButtonGetNumberOfThreads;
    private javax.swing.JButton jButtonListOfMembersForThreads;
    private javax.swing.JButton jButtonNumberOfThreadsForMember;
    private javax.swing.JButton jButtonRemoveModerator;
    private javax.swing.JLabel jLabelCurrentMessage;
    private javax.swing.JLabel jLabelHead;
    private javax.swing.JLabel jLabelMembersList;
    private javax.swing.JLabel jLabelModeratorsList;
    private javax.swing.JLabel jLabelNumberOfThreads;
    private javax.swing.JLabel jLabelNumberOfThreadsForMember;
    private javax.swing.JLabel jLabelPolicies;
    private javax.swing.JLabel jLabelSubForumList;
    private javax.swing.JList jListFollowersToMember;
    private javax.swing.JList jListMembers;
    private javax.swing.JList jListModerators;
    private javax.swing.JList jListSubForums;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    private void updateListOfSubForums() {
        Forum f = CurrentStatus.currForum;
        DefaultListModel listModel = new DefaultListModel();
        List<SubForum> listOfSubForums;
        listOfSubForums = CurrentStatus.currUser.viewSubForums(f.getForumName());
        for (SubForum sf : listOfSubForums) {
            listModel.addElement(sf);
        }
        this.jListSubForums.setModel(listModel);
    }

    private void updateListOfMembers() {
        DefaultListModel listModel = new DefaultListModel();
        List<Member> listOfMembers;
        Admin admin = (Admin) CurrentStatus.currUser;
        listOfMembers = admin.getAllForumMembers();
        for (Member member : listOfMembers) {
            if (member instanceof Admin) {
                continue;
            }
            listModel.addElement(member);
        }
        this.jListMembers.setModel(listModel);
    }

    private void updateListOfModerators(String sf) {
        DefaultListModel listModel = new DefaultListModel();
        List<Moderator> listOfModerators;
        Admin admin = (Admin) CurrentStatus.currUser;
        listOfModerators = admin.getAllModeratorsInSubForum(CurrentStatus.currForum.getForumName(), sf);
        for (Member member : listOfModerators) {
            if (member instanceof Admin) {
                continue;
            }
            if (member instanceof Moderator) {
                listModel.addElement(member);
            }
        }
        this.jListModerators.setModel(listModel);
    }
}
