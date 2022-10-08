package com.nridwan.model;

import java.util.ArrayList;

public class Option {
    private ArrayList<String> subpkgs;

    public Option(String[] args) throws Error {
        subpkgs = new ArrayList<>();
        String command = null;
        int count = 1;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (count == 0) throw new Error("Invalid options");
                command = arg;
                count = 0;
            } else if (command == null) {
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
}
