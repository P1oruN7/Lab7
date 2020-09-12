package common.commands;

import common.Command;

/**
 * Метод проверки (нужен на серваке)
 */
public class Checking implements Command {
    private static final long serialVersionUID = 6529685098267757690L;
    @Override
    public void execute(String par1) {
    }

    @Override
    public String getInfo() {
        return null;
    }
}
