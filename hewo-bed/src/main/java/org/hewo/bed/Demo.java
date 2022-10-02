package org.hewo.bed;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.util.UUID;

public class Demo {
    public static void main(String[] args) {
        System.out.println("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");

        String token = "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";
        try {
            File file = new File("C:\\Users\\Administrator\\Desktop\\_shop\\hewo-bed");

            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("PRIVATE-TOKEN",token);
            /**Git.cloneRepository()
                    .setURI("https://github.com/Jiangzchen/hewo-bed.git")
                    .setDirectory(file)
                    .setCredentialsProvider(credentialsProvider)
                    .call();**/



            Git.open(file).add().addFilepattern(".").call();
            Git.open(file).commit().setMessage(UUID.randomUUID().toString()).call();


             Git.open(file).push().setCredentialsProvider(credentialsProvider)
                    .setRemote("origin").call();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
