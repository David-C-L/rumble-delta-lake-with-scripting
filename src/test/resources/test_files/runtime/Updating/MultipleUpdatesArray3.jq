(:JIQS: ShouldRun; Output="[ 1, 2, 5, 4 ]" :)
copy $je := [1 to 4]
modify (delete $je[[3]], insert 5 into $je at position 3, replace value of $je[[3]] with 6)
return $je