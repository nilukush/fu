import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FUMain {
    private static final String PATH = "";
    private static final String FU = "fu";
    private static final String ACCESS_TOKEN = "ekLfH5y4a2gAAAAAAAArGoXr7-cBDZhN7iuokCnuBwmj5aThyX0AEQUNWfWx8y9s";

    public static void main(@NotNull String[] args) throws DbxException, IOException {
        // Create Dropbox client
        DbxClientV2 client = new DbxClientV2(DbxRequestConfig.newBuilder(FU).build(), ACCESS_TOKEN);

        // Get current account info
        getCurrentAccountInfo(client);

        // Get files and folder metadata from Dropbox root directory
        getFolders(client);

        // Upload to Dropbox
        uploadFile(args[0], client);
    }

    private static void getCurrentAccountInfo(@NotNull DbxClientV2 client) throws DbxException {
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
    }

    private static void getFolders(@NotNull DbxClientV2 client) throws DbxException {
        DbxUserFilesRequests files = client.files();
        ListFolderResult result = files.listFolder(PATH);
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = files.listFolderContinue(result.getCursor());
        }
    }

    private static void uploadFile(String arg, DbxClientV2 client) throws IOException, DbxException {
        if (StringUtils.isEmpty(arg)) {
            System.out.println("USAGE: ./gradlew run -Pfp=\"['<filepath>']\"");
            System.out.println("EXAMPLE: ./gradlew run -Pfp=\"['/Users/nilesh.kumar/Downloads/Spring_Boot_in_Action.pdf']\"");
        }

        File file = new File(arg);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (null == listFiles) return;

            for (File c : listFiles) {
                uploadFile(c.getAbsolutePath(), client);
            }
        } else {
            try (InputStream in = new FileInputStream(arg)) {
                client.files().uploadBuilder(arg)
                        .uploadAndFinish(in);
            }
        }
    }
}
