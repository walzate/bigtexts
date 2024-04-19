A = load './4300.txt.utf-8' using PigStorage(':');
B = foreach A generate flatten(TOKENIZE((chararray)$0)) as word;
C = group B by word;
D = foreach C generate COUNT(B), group;
store D into './4300.txt.utf-8-result-4';

A = load '2014-11-22-19-23-57/1.txt' using PigStorage(':');
B = foreach A generate flatten(TOKENIZE($0));
