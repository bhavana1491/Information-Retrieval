import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class wordCount {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, Text>{

    private static Text docId = new Text();
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
	docId.set(itr.nextToken());
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        context.write(word, docId);
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,Text,Text,Text> {
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
	HashMap<String,Integer> listings = new HashMap<String,Integer>();
	int count =0;
	String res = new String();
	res = "";
      for (Text val : values) {
        String str = val.toString();
	if(listings!=null && listings.get(str)!=null){
 
		count=(int)listings.get(str);
 
		listings.put(str, ++count);
 
	}
	else{
		listings.put(str,1);
	}

      }
	for (String k : listings.keySet()){
		int v = listings.get(k);
		res += "\t" + k + ":" + v; 		
	}
      context.write(key, new Text(res));
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(wordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
