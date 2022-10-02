package org.hewo.bed.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitUtil {

    private final static String GIT = ".git";

    /**
     * 将文件列表提交到git仓库中
     * @param gitRoot git仓库目录
     * @param files 需要提交的文件列表
     * @return 返回本次提交的版本号
     * @throws IOException
     */
    public static String commitToGitRepository(String gitRoot, List<String> files) throws Exception {
        if (StringUtils.isNotBlank(gitRoot) && files != null && files.size() > 0) {

            File rootDir = new File(gitRoot);

            //初始化git仓库
            if (new File(gitRoot + File.separator + GIT).exists() == false) {
                Git.init().setDirectory(rootDir).call();
            }

            //打开git仓库
            Git git = Git.open(rootDir);
            //判断是否有被修改过的文件
            List<DiffEntry> diffEntries = git.diff()
                    .setPathFilter(PathFilterGroup.createFromStrings(files))
                    .setShowNameAndStatusOnly(true).call();
            if (diffEntries == null || diffEntries.size() == 0) {
                throw new Exception("提交的文件内容都没有被修改，不能提交");
            }
            //被修改过的文件
            List<String> updateFiles=new ArrayList<String>();
            DiffEntry.ChangeType changeType;
            for(DiffEntry entry : diffEntries){
                changeType = entry.getChangeType();
                switch (changeType) {
                    case ADD:
                        updateFiles.add(entry.getNewPath());
                        break;
                    case COPY:
                        updateFiles.add(entry.getNewPath());
                        break;
                    case DELETE:
                        updateFiles.add(entry.getOldPath());
                        break;
                    case MODIFY:
                        updateFiles.add(entry.getOldPath());
                        break;
                    case RENAME:
                        updateFiles.add(entry.getNewPath());
                        break;
                }
            }
            //将文件提交到git仓库中，并返回本次提交的版本号
            AddCommand addCmd = git.add();
            for (String file : updateFiles) {
                addCmd.addFilepattern(file);
            }
            addCmd.call();

            CommitCommand commitCmd = git.commit();
            for (String file : updateFiles) {
                commitCmd.setOnly(file);
            }
            RevCommit revCommit = commitCmd.setCommitter("927764151@qq.com", "19981005chen")
                    .setMessage("publish").call();
            return revCommit.getName();
        }
        return null;
    }

}