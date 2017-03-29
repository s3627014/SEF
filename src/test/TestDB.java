package test;
import main.*;
import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class TestDB {

	Connection con;
	
	@Before
	public void beforeClass(){
		con=null;
	}
	
	@Test
	public void testConnection() throws Exception {
		
		Database.Connect();
	}

}
