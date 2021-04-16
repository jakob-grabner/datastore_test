# datastore_test
Example project showcasing that a robolectric test using datastore fails on windows


This repo contains two tests. First one creating two files and renaming the first to the second one. 
Second one uses DataStore and write twice.

Both tests are working on linux (I tried using wsl ond windows) but fail on windows.

DataStore Test fails with

```
java.io.IOException: Unable to rename C:\Users\jag\AppData\Local\Temp\robolectric-Method_dataStore_writeTwice_Test17552740868889124991\com.jakob.datastorerobotest-dataDir\files\datastore\settings.preferences_pb.tmp.This likely means that there are multiple instances of DataStore for this file. Ensure that you are only creating a single instance of datastore for this file.

	at androidx.datastore.core.SingleProcessDataStore.writeData$datastore_core(SingleProcessDataStore.kt:433)
	at androidx.datastore.core.SingleProcessDataStore.transformAndWrite(SingleProcessDataStore.kt:410)
	at androidx.datastore.core.SingleProcessDataStore$transformAndWrite$1.invokeSuspend(SingleProcessDataStore.kt)
	at |b|b|b(Coroutine boundary.|b(|b)
	at kotlinx.coroutines.CompletableDeferredImpl.await(CompletableDeferred.kt:86)
	at com.jakob.datastorerobotest.ExampleDataSToreRobolectricTest$dataStore_writeTwice_Test$1.invokeSuspend(ExampleUnitTest.kt:67)
```

Renaming test fails because `val renameResult = firstFile.renameTo(File(myfolder, "second.txt"))` renameResult returns false.
