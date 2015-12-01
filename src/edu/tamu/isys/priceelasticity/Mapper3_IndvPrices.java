package edu.tamu.isys.pricing;

/*
 * This is mapper class to convert the input from file into key value pairs 
 * output format for mapper is as follows 
 * key =  Book , value = list of all the corresponding values inclusing new calculated book prices
 * 
 */

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.io.Text;


public class MyMapper extends Mapper < LongWritable, Text, Text, Text > {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		String line = value.toString();
		String[] parts = line.split("_"); 
		//if (parts.length > 10)  {
		
		//String[] parts = line.split("_"); 
		//String genre = parts[2];
		
String	DM	=	parts[0];
String	b	=	parts[1];
String	pStr	=	parts[2];
double p = Double.parseDouble(pStr);

String	qStr	=	parts[3];
int q = Integer.parseInt(qStr) ;

String	bt	=	parts[4];
String	oppStr	=	parts[5];
double opp = Double.parseDouble(oppStr);

String	GRStr	=	parts[6];
double GR = Double.parseDouble(GRStr);

String	eStr	=	parts[7];
double e = Double.parseDouble(eStr);

String	p1Str	=	parts[8];
double p1 = Double.parseDouble(p1Str);

String	boolCStr	=	parts[9];
int boolC = Integer.parseInt(boolCStr) ;

String	PublishYearStr	=	parts[10];

double PublishYear = Double.parseDouble(PublishYearStr);


String	dmYearStr	=	DM.substring(0,4);
double dmYear = Double.parseDouble(dmYearStr);

Double	P12	 = 0d;
Double	P3	 = 0d;

//P12 = (((1+ GrowthRate)*(ActualPrice/2)*(elasticity/(1+elasticity))*Is_current ) + (((1+ GrowthRate)*(PredictedPrice/2)*(elasticity/(1+elasticity))*(1-Is_current) ) )

P12 = (((1+ GR)*(p/2)*(e/(1+e))*boolC ) + ((1+ GR)*(p1/2)*(e/(1+e))*(1-boolC) ) );
//P3= OriginalPublishPrice(1- ((CurrentYear-PublishYear)/Age))

//System.out.println(dmYear);
//System.out.println(PublishYear);
//System.out.println(opp);
P3 = opp*(1- ((dmYear - PublishYear)/10));
//System.out.println(P12);
//System.out.println(P3);
		//}
		
		 String P12Str = Double.toString(P12);
		 String P3Str = Double.toString(P3);
		//String bookPrices = DM + "_" + b + "_" + p + "_" + q + "_" + bt + "_" + opp + "_" + GR + "_" + e + "_" + p1 + "_" + boolC  + "_" + 	PublishYear;
		String bookPrices = line + "_" + P12Str + "_" + P3Str;
		
		
		context.write(new Text(b), new Text(bookPrices));
		//context.write(new Text(b), new Text(bookPrices));
		//String line = key.toString();
		//context.write( new Text(line), new Text(value));
				}

			
		}

	
