package org.ryuu.libgdxlibrary.util;


import com.badlogic.gdx.files.FileHandle;
import org.ryuu.functional.IFunc1Arg;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileHandles {
    private static final Stack<FileHandle> directoryStack = new Stack<>();

    private FileHandles() {
    }

    public static List<FileHandle> get(FileHandle handle, IFunc1Arg<FileHandle, Boolean> condition) {
        ArrayList<FileHandle> targets = new ArrayList<>();
        if (!handle.exists()) {
            throw new RuntimeException("handle not exist: " + handle);
        }

        loadFileOrPushDirectoryIntoStack(handle, condition, targets);
        while (!directoryStack.isEmpty()) {
            handle = directoryStack.pop();
            FileHandle[] handles = handle.list();
            for (FileHandle fileHandle : handles) {
                loadFileOrPushDirectoryIntoStack(fileHandle, condition, targets);
            }
        }

        return targets;
    }

    private static void loadFileOrPushDirectoryIntoStack(FileHandle fileHandle, IFunc1Arg<FileHandle, Boolean> condition, List<FileHandle> targets) {
        if (condition.invoke(fileHandle)) {
            targets.add(fileHandle);
        }

        if (fileHandle.isDirectory()) {
            directoryStack.push(fileHandle);
        }
    }
}
