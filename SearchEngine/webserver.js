var solr = require('solr-client');
var express = require('express');
var app = express()
var http = require('http').Server(app);
var bodyParser= require('body-parser');
var path = require('path');
var fs = require('fs'); 
var parse = require('csv-parse');

app.use(express.static(path.join(__dirname, '.')));

http.listen(3000,function(err){
		if(err){
   	console.log(err);
   }else{
	   console.log('Http running on 3000');	
			
			}});




app.get('/',function(req,res){
  //res.sendFile('/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_4/HW4/query_search.html');
	res.sendFile(__dirname + '/query_search.html');
});
	
var client = solr.createClient('127.0.0.1',8983,'myexample','/solr');
/*app.get('/suggest',function(req,res){
  //res.sendFile('/Users/Bhavana/Desktop/Spring_Sem/IR/Assignment_4/HW4/query_search.html');
	var query = client.createQuery()
				  .q(req.query.query);
client.search(query,function(err,obj){
   if(err){
   	console.log(err);
   }else{
	   //console.log(obj);
	   //var rows = obj.response.numFound;
	   //console.log(rows);
   	   //console.log(obj.response.docs[0].title);
	   console.log(obj);
	  //console.log(obj.response.docs.length);
   }
});

});*/
app.get('/query_search',function(req,res){
	//console.log(req.query.query);
	//console.log(req.param('sort'));
if(req.param('sort') == 'solr_results'){
var query = client.createQuery()
				  .q(req.query.query);
client.search(query,function(err,obj){
   if(err){
   	console.log(err);
   }else{
	   //console.log(obj);
	   //var rows = obj.response.numFound;
	   //console.log(rows);
   	   //console.log(obj.response.docs[0].title);
	   res.send(obj.response);
	  //console.log(obj.response.docs.length);
   }
});
}
else if(req.param('sort') == 'pr_results'){
	var query = client.createQuery()
				  .q(req.query.query)
				  .sort({'pageRankFile':'desc'});
client.search(query,function(err,obj){
   if(err){
   	console.log(err);
   }else{
	   //console.log(obj);
	   //var rows = obj.response.numFound;
	   //console.log(rows);
   	  // console.log(obj.response.docs[0].title);
	   res.send(obj.response);
	  //console.log(obj.response.docs.length);
   }
});
	
}
	
});

app.get('/createmap',function(req,res){
var csvData=[];
fs.createReadStream("mapCNNDataFile.csv")
    .pipe(parse({delimiter: ','}))
    .on('data', function(csvrow) {
        //console.log(csvrow);
        //do something with csvrow
        csvData.push(csvrow);        
    })
    .on('end',function() {
      //do something wiht csvData
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify(csvData));
      //console.log(csvData);
    });
});
