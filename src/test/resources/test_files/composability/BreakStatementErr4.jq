(:JIQS: ShouldNotCompile; ErrorCode="SCCP0004"; ErrorMetadata="LINE:5:COLUMN:14:" :)
switch (2)
case 1 + 1 return "foo";
case 2 + 2 return "bar";
case 3 return break loop;
default return "none";

(: break not allowed outside of while or flwor :)