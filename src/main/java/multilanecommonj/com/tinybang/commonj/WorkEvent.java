package com.tinybang.commonj;


/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Jul 8, 2009
 * Time: 9:23:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkEvent<T extends Work> {

    private T entryInfo;
    private Throwable erroInfo;
    private String status;
    private boolean trigger = true;
    private String step;

    public enum Staus{
    	START,
    	PROCESS,
    	END,
    	FAIL;
    }

    public T getEntryInfo() {
        return entryInfo;
    }

    public void setEntryInfo(T entryInfo) {
        this.entryInfo = entryInfo;
    }

    public Throwable getErroInfo() {
        return erroInfo;
    }

    public void setErroInfo(Throwable erroInfo) {
        this.erroInfo = erroInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public boolean isTrigger() {
        return trigger;
    }

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}