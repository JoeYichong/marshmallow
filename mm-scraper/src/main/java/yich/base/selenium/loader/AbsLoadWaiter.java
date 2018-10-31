package yich.base.selenium.loader;


import yich.base.dbc.Require;

abstract public class AbsLoadWaiter implements LoadWaiter{
    private int loadTimeoutSec = 10;

    @Override
    public int getTimeoutSec() {
        return loadTimeoutSec;
    }

    @Override
    public LoadWaiter setTimeoutSec(int timeoutSec) {
        Require.argument(timeoutSec > 0, timeoutSec, "timeoutSec > 0");
        this.loadTimeoutSec = timeoutSec;
        return this;
    }

}
