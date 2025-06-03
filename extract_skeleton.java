import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Skeleton {
    private int numRows;
    private int numCols;
    private int minVal;
    private int maxVal;
    private int newMinVal;
    private int newMaxVal;
    int[][] ZFAry;
    int[][] skeletonAry;
    int distanceChoice;

    public Skeleton(int numRows, int numCols, int minVal, int maxVal, int distanceChoice){
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.distanceChoice = distanceChoice;

        ZFAry = new int[numRows + 2][numCols + 2];
        skeletonAry = new int[numRows + 2][numCols + 2];
    }

    void setZero(int[][] arr){
        for(int i = 0; i <= numRows + 1; i++){
            for(int j = 0; j <= numCols + 1; j++){
                arr[i][j] = 0;
            }
        }
    }

    void loadImage(BufferedReader inFile) throws IOException{
        int i = 1;
        String data = inFile.readLine();
        while(data != null){
            data = data.trim();
            if(!data.isEmpty()){
                String[] tempData = data.split(" ");
                for(int j = 0; j < tempData.length; j++){
                    ZFAry[i][j + 1] = Integer.parseInt(tempData[j]);
                }
            }
            data = inFile.readLine();
            i++;
        }
    }

    int findMax(int arr[][]){
        int max = 0;
        for(int i = 0; i <= numRows + 1; i++){
            for(int j = 0; j <= numCols + 1; j++){
                if(arr[i][j] > max){
                    max = arr[i][j];
                }
            }
        }
        return max;
    }

    void prettyPrint(BufferedWriter prettyPrintFile, int arr[][])throws IOException{
        String str = Integer.toString(findMax(arr));
        prettyPrintFile.write(numRows + " " + numCols + " " + minVal + " " + str + "\n");
        int width = str.length();
        for(int i = 0; i <= numRows; i++){
            for(int j = 0; j <= numCols; j++){
                if(arr[i][j] > 0){
                    prettyPrintFile.write(Integer.toString(arr[i][j]));
                }
                else{
                    prettyPrintFile.write(".");
                }
                str = Integer.toString(arr[i][j]);
                int WW = str.length();
                while(WW <= width){
                    prettyPrintFile.write(" ");
                    WW++;
                }
            }
            prettyPrintFile.write("\n");
        }
        prettyPrintFile.write("\n");
    }

    void distancePass1(BufferedWriter logFile) throws IOException{
        logFile.write("**Entering distancePass1()**\n");
        for(int i = 1; i <= numRows; i++){
            for(int j = 1; j <= numCols; j++){
                if(ZFAry[i][j] > 0){
                    int a = ZFAry[i-1][j-1];
                    int b = ZFAry[i-1][j];
                    int c = ZFAry[i-1][j+1];
                    int d = ZFAry[i][j-1];
                    int min;

                    if(distanceChoice == 8){
                        min = Math.min(Math.min(a, b), Math.min(c, d));
                        ZFAry[i][j] = min + 1;
                    }
                    else if(distanceChoice == 4){
                        min = Math.min(Math.min(a + 2, b + 1), Math.min(c + 2,d + 1));
                        ZFAry[i][j] = min;
                    }
                }
            }
        }
        logFile.write("**Leaving distancePass1()**\n");
    }

    void distancePass2(BufferedWriter logFile) throws IOException{
        logFile.write("***Entering distancePass2()**\n");
        for(int i = numRows; i >= 1; i--){
            for(int j = numCols; j >= 1; j--){
                if(ZFAry[i][j] > 0){
                    int a = ZFAry[i][j+1];
                    int b = ZFAry[i+1][j-1];
                    int c = ZFAry[i+1][j];
                    int d = ZFAry[i+1][j+1];
                    int min;

                    if(distanceChoice == 8){
                        int min1 = Math.min(Math.min(a + 1, b + 1), Math.min(c + 1, d + 1));
                        min = Math.min(min1, ZFAry[i][j]);
                        ZFAry[i][j] = min;
                    }
                    else if(distanceChoice == 4){
                        int min1 = Math.min(Math.min(a + 1, b + 2), Math.min(c + 1, d + 2));
                        min = Math.min(min1, ZFAry[i][j]);
                        ZFAry[i][j] = min;
                    }
                }
            }
        }
        logFile.write("**Leaving distancePass2()**\n");
    }

    void distanceTransform(BufferedWriter prettyPrintFile, BufferedWriter logFile) throws IOException{
        logFile.write("**Entering distanceTransform()**\n");
        distancePass1(logFile);
        prettyPrintFile.write("1st pass distance transform with choice = " + distanceChoice + "\n");
        prettyPrint(prettyPrintFile, ZFAry);
        distancePass2(logFile);
        prettyPrintFile.write("2nd pass distance transform with choice = " + distanceChoice + "\n");
        prettyPrint(prettyPrintFile, ZFAry);
        logFile.write("**Leaving distanceTransform()**\n");
    }

    boolean isLocalMaxima(int i, int j){
        if(ZFAry[i][j] >  0){
            int a = ZFAry[i-1][j-1];
            int b = ZFAry[i-1][j];
            int c = ZFAry[i-1][j+1];
            int d = ZFAry[i][j-1];
            int e = ZFAry[i][j+1];
            int f = ZFAry[i+1][j-1];
            int g = ZFAry[i+1][j];
            int h = ZFAry[i+1][j+1];

            if(distanceChoice == 8){
                if(ZFAry[i][j] >= a && ZFAry[i][j] >= b && ZFAry[i][j] >= c && ZFAry[i][j] >= d && ZFAry[i][j] >=e && ZFAry[i][j] >= f && ZFAry[i][j] >= g && ZFAry[i][j] >= h){
                    return true;
                }
            }
            else if(distanceChoice == 4){
                if(ZFAry[i][j] >= b && ZFAry[i][j] >= d && ZFAry[i][j] >= e && ZFAry[i][j] >= g){
                    return true;
                }
            }
        }
        return false;
    }

    void computeLocalMaxima(BufferedWriter logFile) throws IOException{
        logFile.write("**Entering computeLocalMaxima()**\n");
        for(int i = 1; i <= numRows; i++){
            for(int j = 1; j <= numCols; j++){
                if(isLocalMaxima(i,j)){
                    skeletonAry[i][j] = ZFAry[i][j];
                }
                else{
                    skeletonAry[i][j] = 0;
                }
            }
        }
        logFile.write("**Leaving computeLocalMaxima()**\n");
    }

    void extractSkeleton(BufferedWriter logFile, BufferedWriter skeletonFile) throws IOException{
        logFile.write("**Entering extractSkeleton()**\n");
        int max = findMax(skeletonAry);
        skeletonFile.write(numRows + " " + numCols + " " + minVal + " " + max + "\n");
        for(int i = 1; i <= numRows; i++){
            for(int j = 1; j <= numCols; j++){
                if(skeletonAry[i][j] > 0){
                    skeletonFile.write(i + " " + j + " " + skeletonAry[i][j] + "\n");
                }
            }
        }
        logFile.write("**Leaving extractSkeleton()**\n");
    }

    void printSkeleton(String fileName, BufferedWriter prettyPrintFile) throws IOException{
        BufferedReader SkeletonFile = null;
        try{
            SkeletonFile = new BufferedReader(new FileReader(fileName));
        }
        catch(Exception e){
            System.out.println("Can't read skeletonFile.txt. It needs to be in .txt format\n");
            System.exit(1);
        }
        String data = SkeletonFile.readLine();
        while(data != null){
            prettyPrintFile.write(data + "\n");
            data = SkeletonFile.readLine();
        }
        SkeletonFile.close();
    }

    void writeSkeletonImage(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        int max = findMax(skeletonAry);
        writer.write(numRows + " " + numCols + " " + minVal + " " + max + "\n");
        for (int i = 1; i <= numRows; i++) {
            for (int j = 1; j <= numCols; j++) {
                writer.write(skeletonAry[i][j] + " ");
            }
            writer.write("\n");
        }
        writer.close();
    }

    void compression(BufferedWriter skeletonFile, BufferedWriter prettyPrintFile, BufferedWriter logFile, String skeletonFileName) throws IOException{
        logFile.write("**Entering compression()**\n");
        computeLocalMaxima(logFile);
        String imageFileName = skeletonFileName.substring(0, skeletonFileName.length() - 4) + "_image.txt";
        writeSkeletonImage(imageFileName);
        prettyPrintFile.write("**Local maxima, skeleAry with choice = " + distanceChoice + "**\n");
        prettyPrint(prettyPrintFile, skeletonAry);
        extractSkeleton(logFile, skeletonFile);
        skeletonFile.close();
        prettyPrintFile.write("**In compression() Below is skeleton with choice = " + distanceChoice + "**\n");
        printSkeleton(skeletonFileName, prettyPrintFile);
        logFile.write("**Leaving compression()**\n");
    }

    void loadSkeleton(BufferedReader skeletonFile, BufferedWriter logFile) throws IOException{
        logFile.write("**Entering loadSkeleton()**\n");
        String data = skeletonFile.readLine(); //getting rid of header
        data = skeletonFile.readLine();
        while(data != null){
            String[] tempData = data.split(" ");
            int i = Integer.parseInt(tempData[0]);
            int j = Integer.parseInt(tempData[1]);
            int value = Integer.parseInt(tempData[2]);
            ZFAry[i][j] = value;
            data = skeletonFile.readLine();
        }
        logFile.write("**Leaving loadSkeleton()**\n");
    }

    void expansionPass1(BufferedWriter logFile) throws IOException{
        logFile.write("**Entering expansionPass1**\n");
        for(int i = 1; i <= numRows; i++){
            for(int j = 1; j <= numCols; j++){
                if(ZFAry[i][j] == 0){
                    int a = ZFAry[i-1][j-1];
                    int b = ZFAry[i-1][j];
                    int c = ZFAry[i-1][j+1];
                    int d = ZFAry[i][j-1];
                    int e = ZFAry[i][j+1];
                    int f = ZFAry[i+1][j-1];
                    int g = ZFAry[i+1][j];
                    int h = ZFAry[i+1][j+1];
                    if(distanceChoice == 8){
                        int max = Math.max(Math.max(Math.max(Math.max(a-1, b-1), Math.max(c-1, d-1)), Math.max(Math.max(e-1, f-1), Math.max(g-1, h-1))), ZFAry[i][j]);
                        ZFAry[i][j] = max;
                    }
                    else if(distanceChoice == 4){
                        int max = Math.max(Math.max(Math.max(Math.max(a-2, b-1), Math.max(c-2, d-1)), Math.max(Math.max(e-1, f-2), Math.max(g-1, h-2))), ZFAry[i][j]);
                        ZFAry[i][j] = max;
                    }
                }
            }
        }
        logFile.write("**Leaving expansionPass1**\n");
    }

    void expansionPass2(BufferedWriter logFile) throws IOException{
        logFile.write("**Entering expansionPass2()**\n");
        for(int i = numRows; i >= 1; i--){
            for(int j = numCols; j >= 1; j--){
                int a = ZFAry[i-1][j-1];
                int b = ZFAry[i-1][j];
                int c = ZFAry[i-1][j+1];
                int d = ZFAry[i][j-1];
                int e = ZFAry[i][j+1];
                int f = ZFAry[i+1][j-1];
                int g = ZFAry[i+1][j];
                int h = ZFAry[i+1][j+1];
                if(distanceChoice == 8){
                    int max = Math.max(Math.max(Math.max(Math.max(a-1, b-1), Math.max(c-1, d-1)), Math.max(Math.max(e-1, f-1), Math.max(g-1, h-1))), ZFAry[i][j]);
                    ZFAry[i][j] = max;
                }
                else if (distanceChoice == 4){
                    int max = Math.max(Math.max(Math.max(Math.max(a-2, b-1), Math.max(c-2, d-1)), Math.max(Math.max(e-1, f-2), Math.max(g-1, h-2))), ZFAry[i][j]);
                    ZFAry[i][j] = max;
                }
            }
        }
        logFile.write("**Leaving expansionPass2()**\n");
    }

    void deCompression(BufferedWriter prettyPrintFile, BufferedWriter logFile) throws IOException{
        logFile.write("**Entering deCompression()**\n");
        expansionPass1(logFile);
        prettyPrintFile.write("**1st expansion pass with choice = " + distanceChoice + "**\n");
        prettyPrint(prettyPrintFile, ZFAry);
        expansionPass2(logFile);
        prettyPrintFile.write("**2nd expansion pass with choice = " + distanceChoice + "**\n");
        prettyPrint(prettyPrintFile, ZFAry);
        logFile.write("**Leaving deCompression()**\n");
    }

    void binThreshold(BufferedWriter deCompressedFile) throws IOException{
        for(int i = 1; i <= numRows; i++){
            for(int j = 1; j <= numCols; j++){
                if(ZFAry[i][j] >= 1){
                    deCompressedFile.write(1 + " ");
                }
                else{
                    deCompressedFile.write(0 + " ");
                }
            }
            deCompressedFile.write("\n");
        }
    }
}

public class extract_skeleton {
    public static void main(String[] args) throws IOException{
        if(args.length < 6){
            System.out.println("Not enough arguments");
            return;
        }
        BufferedReader inFile;
        int distanceChoice;
        try{
            inFile = new BufferedReader(new FileReader(args[0]));
        }
        catch(Exception e){
            System.out.println("Can't read inFile through args[0]");
            return;
        }
        try{
            distanceChoice = Integer.parseInt(args[1]);
        }
        catch(Exception e){
            System.out.println("Can't read choice through args[1]");
            return;
        }
        BufferedWriter prettyPrintFile = new BufferedWriter(new FileWriter(args[2]));
        BufferedWriter skeletonFile = new BufferedWriter(new FileWriter(args[3]));
        BufferedWriter deCompressedFile = new BufferedWriter(new FileWriter(args[4]));
        BufferedWriter logFile = new BufferedWriter(new FileWriter(args[5]));
        String header = inFile.readLine();
        String[] headerData = header.split(" ");
        int numRows = Integer.parseInt(headerData[0]);
        int numCols = Integer.parseInt(headerData[1]);
        int minVal = Integer.parseInt(headerData[2]);
        int maxVal = Integer.parseInt(headerData[3]);
        Skeleton skeleton = new Skeleton(numRows, numCols, minVal, maxVal, distanceChoice);
        skeleton.loadImage(inFile);
        prettyPrintFile.write("**Below is the input image**\n");
        skeleton.prettyPrint(prettyPrintFile, skeleton.ZFAry);
        skeleton.distanceTransform(prettyPrintFile, logFile);
        skeleton.compression(skeletonFile, prettyPrintFile, logFile, args[3]);
        skeleton.setZero(skeleton.ZFAry);
        BufferedReader skeletonFile2 = new BufferedReader(new FileReader(args[3]));
        skeleton.loadSkeleton(skeletonFile2, logFile);
        prettyPrintFile.write("**Below is the loaded skeleton with choice = " + distanceChoice + "**\n");
        skeleton.prettyPrint(prettyPrintFile, skeleton.ZFAry);
        skeleton.deCompression(prettyPrintFile, logFile);
        deCompressedFile.write(numRows + " " + numCols + " " + minVal + " " + maxVal + "\n");
        skeleton.binThreshold(deCompressedFile);
        inFile.close();
        prettyPrintFile.close();
        deCompressedFile.close();
        skeletonFile2.close();
        logFile.close();
    }
}