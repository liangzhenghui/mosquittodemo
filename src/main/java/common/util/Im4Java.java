package common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.process.ArrayListOutputConsumer;

public class Im4Java {
	public static void resizeImage(int width,int height,String srcPath, String desPath) {
		try {
			IMOperation op = new IMOperation();
			op.addImage();
			op.resize(width, height);
			op.addImage();
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op, srcPath, desPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}

	}
	
	 /**
     * 获取图片信息
     * @param path 图片路径
     * @return Map {height=, filelength=, directory=, width=, filename=}
     * @throws Exception
     */
    public static Map<String, Object> getImageInfo(String path) {
        IMOperation op = new IMOperation();
        op.format("%w,%h,%d,%f,%b");
        op.addImage(path);
        IdentifyCmd identifyCmd = new IdentifyCmd();
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identifyCmd.setOutputConsumer(output);
        try {
			identifyCmd.run(op);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
        ArrayList<String> cmdOutput = output.getOutput();
        if (cmdOutput.size() != 1) return null;
        String line = cmdOutput.get(0);
        String[] arr = line.split(",");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("width", Integer.parseInt(arr[0]));
        info.put("height", Integer.parseInt(arr[1]));
        info.put("directory", arr[2]);
        info.put("filename", arr[3]);
        info.put("filelength", Integer.parseInt(arr[4]));
        return info;
    }
}
