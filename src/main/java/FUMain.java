import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FUMain {
    private static final String PATH = "";
    private static final String FU = "fu";
    private static final String ACCESS_TOKEN = "ekLfH5y4a2gAAAAAAAArGoXr7-cBDZhN7iuokCnuBwmj5aThyX0AEQUNWfWx8y9s";

    public static void main(String args[]) throws DbxException, IOException {
        // Create Dropbox client
        DbxClientV2 client = new DbxClientV2(DbxRequestConfig.newBuilder(FU).build(), ACCESS_TOKEN);

        // Get current account info
        getCurrentAccountInfo(client);

        // Get files and folder metadata from Dropbox root directory
        getFolders(client);

        // Upload resume to Dropbox
        uploadFile(args[0], client);
    }

    private static void getCurrentAccountInfo(DbxClientV2 client) throws DbxException {
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
    }

    private static void getFolders(DbxClientV2 client) throws DbxException {
        ListFolderResult result = client.files().listFolder(PATH);
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }

    private static void uploadFile(String arg, DbxClientV2 client) throws IOException, DbxException {
        String fpath = arg;
        if (StringUtils.isEmpty(fpath)) {
            System.out.println("USAGE: fu <file_path>");
        }

        try (InputStream in = new FileInputStream(fpath)) {
            client.files().uploadBuilder(fpath)
                    .uploadAndFinish(in);
        }
    }
}
