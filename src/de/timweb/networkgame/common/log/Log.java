package de.timweb.networkgame.common.log;


public interface Log {
	public final static int	ALL		= 0;
	public final static int	DEBUG	= 1;
	public final static int	INFO	= 2;
	public final static int	WARN	= 3;
	public final static int	ERROR	= 4;

	public void setLevel(int level);

	public void debug(String msg);

	public void info(String msg);

	public void warn(String msg);

	public void error(String msg);
}
