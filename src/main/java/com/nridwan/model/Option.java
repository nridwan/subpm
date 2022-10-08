package com.nridwan.model;

import java.util.ArrayList;

public class Option {
    private ArrayList<String> subpkgs;
    private String actionType;

    public Option(String[] args) throws Error {
        ArrayList<String> actions = new ArrayList<>();
        actions.add("install");
        actions.add("update");
        actions.add("reset");
        subpkgs = new ArrayList<>();
        actionType = "install";
        String command = null;
        int count = 1;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (count == 0) throw new Error("Invalid options");
                command = arg;
                count = 0;
            } else if (command == null) {
                if(actions.contains(args[0])) {
                    actionType = args[0];
                    continue;
                }
                throw new Error("Invalid options");
            } else if (command.equals("--files")) {
                subpkgs.add(arg);
                count++;
            }
        }
        if(subpkgs.size() == 0) {
            subpkgs.add("subpkg.yaml");
            subpkgs.add("subpkg.local.yaml");
        }
    }

    public ArrayList<String> getSubpkgs() {
        return subpkgs;
    }

    public void setSubpkgs(ArrayList<String> subpkgs) {
        this.subpkgs = subpkgs;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
