/**        
 * StrongUuidGenerator.java Create on 2013-5-30 下午8:59:16 
 */
package cn.edu.ahpu.common.uuid;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 
 * @author <a href="mailto:bsli@ustcinfo.com">li.binsong</a>
 * 
 */
public class StrongUuidGenerator {

	protected static TimeBasedGenerator timeBasedGenerator;

	static {
		timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}


	public static String getNextId() {
		return timeBasedGenerator.generate().toString();
	}
}
