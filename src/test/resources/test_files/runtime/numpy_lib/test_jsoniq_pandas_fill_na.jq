(:JIQS: ShouldRun; Output="({ "label" : 0, "binaryLabel" : 0, "name" : "a", "age" : 20, "weight" : 50, "booleanCol" : false, "nullCol" : 1, "stringCol" : "i am data entry 1", "stringArrayCol" : [ "i", "am", "data", "entry", "1" ], "intArrayCol" : [ 1, 2, 3 ], "doubleArrayCol" : [ 1, 2, 3 ], "doubleArrayArrayCol" : [ 1, 2, 3 ] }, { "label" : 1, "binaryLabel" : 0, "name" : "b", "age" : 21, "weight" : 55.3, "booleanCol" : false, "nullCol" : 1, "stringCol" : "i am data entry 2", "stringArrayCol" : [ "i", "am", "data", "entry", "2" ], "intArrayCol" : [ 4, 5, 6 ], "doubleArrayCol" : [ 4, 5, 6 ], "doubleArrayArrayCol" : [ 4, 5, 6 ] }, { "label" : 2, "binaryLabel" : 0, "name" : "c", "age" : 22, "weight" : 60.6, "booleanCol" : false, "nullCol" : 1, "stringCol" : "i am data entry 3", "stringArrayCol" : [ "i", "am", "data", "entry", "3" ], "intArrayCol" : [ 7, 8, 9 ], "doubleArrayCol" : [ 7, 8, 9 ], "doubleArrayArrayCol" : [ 7, 8, 9 ] }, { "label" : 3, "binaryLabel" : 1, "name" : "d", "age" : 23, "weight" : 65.9, "booleanCol" : 1, "nullCol" : 1, "stringCol" : "i am data entry 4", "stringArrayCol" : [ "i", "am", "data", "entry", "4" ], "intArrayCol" : [ 1, 4, 7 ], "doubleArrayCol" : [ 1, 4, 7 ], "doubleArrayArrayCol" : [ 1, 4, 7 ] }, { "label" : 4, "binaryLabel" : 1, "name" : "e", "age" : 24, "weight" : 70.3, "booleanCol" : true, "nullCol" : 1, "stringCol" : "i am data entry 5", "stringArrayCol" : [ "i", "am", "data", "entry", "5" ], "intArrayCol" : [ 2, 5, 8 ], "doubleArrayCol" : [ 2, 5, 8 ], "doubleArrayArrayCol" : [ 2, 5, 8 ] }, { "label" : 5, "binaryLabel" : 1, "name" : "f", "age" : 25, "weight" : 75.6, "booleanCol" : true, "nullCol" : 1, "stringCol" : "i am data entry 6", "stringArrayCol" : [ "i", "am", "data", "entry", "6" ], "intArrayCol" : [ 3, 6, 9 ], "doubleArrayCol" : [ 3, 6, 9 ], "doubleArrayArrayCol" : [ 3, 6, 9 ] }, { "test" : [ 1, 2, -34 ], "test2" : -34, "test3" : { "test4" : [ 1, 4, 2 ], "test5" : -34, "test6" : { "test7" : [ 1, -34, -34, -34 ] } } }, { "test1" : [ { "test2" : [ 1, 2, 3 ] }, { "test3" : [ 1, 2, [ 1, 2, 3 ] ] }, { "test4" : { "test5" : [ 1, 2, 3 ] } } ] }, { "test1" : [ { "test2" : "not_null" }, { "test3" : [ 1, 2, "not_null" ] }, { "test4" : { "test5" : "not_null" } } ] })":)
import module namespace pandas = "jsoniq_pandas.jq";


json-file("../../../queries/sample-na-data.json")=>pandas:fillna({"value": 1}),
pandas:fillna({"test": [1,2, null], "test2": null, "test3": {"test4": [1, 4, 2], "test5": null, "test6": {"test7": [1, null, null, null]}}}, {"value": -34}),
pandas:fillna({"test1": [{"test2": null}, {"test3": [1, 2, null]}, {"test4": {"test5": null}}]}, {"value": [1, 2, 3]}),
pandas:fillna({"test1": [{"test2": null}, {"test3": [1, 2, null]}, {"test4": {"test5": null}}]}, {"value": "not_null"})