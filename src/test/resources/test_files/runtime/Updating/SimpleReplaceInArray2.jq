(:JIQS: ShouldRun; Output="[ 1, 2, 5, 6 ]" :)
copy json $je := [1 to 4]
modify (replace json value of $je[[3]] with 5, replace json value of $je[[4]] with 6)
return $je