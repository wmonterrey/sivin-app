package ni.gob.minsa.sivin.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class CalcularEdad {
	
	
	public Period calcDiff(Date startDate,Date endDate)
    {
        DateTime START_DT = (startDate==null)?null:new DateTime(startDate);
        DateTime END_DT = (endDate==null)?null:new DateTime(endDate);

        Period period = new Period(START_DT, END_DT);

        return period;

    }

}
