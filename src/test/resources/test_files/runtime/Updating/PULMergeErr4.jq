(:JIQS: ShouldCrash; ErrorCode="JNUP0010"; ErrorMetadata="LINE:3:COLUMN:8:" :)
copy $je := { "a" : 1, "b" : 2, "c" : 3 }
modify (rename $je.a as "foo", rename $je.a as "bar")
return $je

(: rename at same object with same selector :)