package io.sqlc;

import android.util.Log;
import org.apache.cordova.*;

import org.json.*;

public class SQLitePlugin extends CordovaPlugin {
//   @Override
//   public boolean execute(String a, JSONArray data, CallbackContext cbc) {
//     switch(a) {
//       case "openDatabaseConnection":
//         openDatabaseConnection(data, cbc);
//         break;
//       case "executeBatch":
//         executeBatch(data, cbc);
//         break;
//       default:
//         return false;
//     }
//     return true;
//   }
   /**
     * Executes the request and returns PluginResult.
     *
     * @param actionAsString The action to execute.
     * @param args   JSONArry of arguments for the plugin.
     * @param cbc    Callback context from Cordova API
     * @return       Whether the action was valid.
     */
    @Override
    public boolean execute(String actionAsString, JSONArray args, CallbackContext cbc) {

        Action action;
        try {
            action = Action.valueOf(actionAsString);
        } catch (IllegalArgumentException e) {
            // shouldn't ever happen
            Log.e(SQLitePlugin.class.getSimpleName(), "unexpected error", e);
            return false;
        }

        try {
            return executeAndPossiblyThrow(action, args, cbc);
        } catch (JSONException e) {
            // TODO: signal JSON problem to JS
            Log.e(SQLitePlugin.class.getSimpleName(), "unexpected error", e);
            return false;
        }
    }

    private boolean executeAndPossiblyThrow(Action action, JSONArray args, CallbackContext cbc)
            throws JSONException {

        boolean status = true;
        JSONObject o;
        String echo_value;
        String dbname;
        int connectionId;

        Log.d(SQLitePlugin.class.getSimpleName(), "action " + action);

        switch (action) {
            case echoStringValue:
                o = args.getJSONObject(0);
                echo_value = o.getString("value");
                cbc.success(echo_value);
                break;

            case open:
//                 o = args.getJSONObject(0);
//                 dbname = o.getString("name");
                // open database and start reading its queue
//                 this.startDatabase(dbname, o, cbc);
                Log.d(SQLitePlugin.class.getSimpleName(), "The plugin is executing the open action.");
                this.openDatabaseConnection(args, cbc);
//                 cbc.success();
                break;

            case close:
                o = args.getJSONObject(0);
//                 connectionId = o.getInt("connectionId");
                // put request in the q to close the db
//                 this.closeDatabase(connectionId, cbc);
//                 cbc.error("Unimplemented: close");
                Log.d(SQLitePlugin.class.getSimpleName(), "Unimplemented: close");
                // Added this success because the success function is what opens the db connection.
                cbc.success();
                break;

            case delete:
                o = args.getJSONObject(0);
                dbname = o.getString("path");

//                 deleteDatabase(dbname, cbc);
                cbc.error("Unimplemented: close");

                break;

            case executeSqlBatch:
            case backgroundExecuteSqlBatch:
//                 JSONObject allargs = args.getJSONObject(0);
//                 JSONArray txargs = allargs.getJSONArray("executes");
//                 Log.d(SQLitePlugin.class.getSimpleName(), "allargs: " + allargs);
//                 Log.d(SQLitePlugin.class.getSimpleName(), "txargs: " + txargs);
//                 JSONObject dbargs = allargs.getJSONObject("dbargs");
//                 dbname = dbargs.getString("dbname");
//                 connectionId = dbargs.getInt("connectionId");

//
//                 if (txargs.isNull(0)) {
//                     cbc.error("INTERNAL PLUGIN ERROR: missing executes list");
//                 } else {
//                     int len = txargs.length();
//                     String[] queries = new String[len];
//                     JSONArray[] jsonparams = new JSONArray[len];
//
//                     for (int i = 0; i < len; i++) {
//                         JSONObject a = txargs.getJSONObject(i);
//                         queries[i] = a.getString("sql");
//                         jsonparams[i] = a.getJSONArray("params");
//                     }
//
//                     // put db query in the queue to be executed in the db thread:
//                     DBQuery q = new DBQuery(queries, jsonparams, cbc);
//                     DBRunner r = dbrmap.get(dbname);
//                     if (r != null) {
//                         try {
//                             r.q.put(q);
//                         } catch(Exception e) {
//                             Log.e(SQLitePlugin.class.getSimpleName(), "couldn't add to queue", e);
//                             cbc.error("INTERNAL PLUGIN ERROR: couldn't add to queue");
//                         }
//                     } else {
//                         cbc.error("INTERNAL PLUGIN ERROR: database not open");
//                     }
//                 }
//                     cbc.error("Unimplemented: executeBatch = args: " + args);
                    executeBatch(args, cbc);

                break;
        }

        return status;
    }

//     /**
//      * Clean up and close all open databases.
//      */
//     @Override
//     public void onDestroy() {
//         while (!dbrmap.isEmpty()) {
//             String dbname = dbrmap.keySet().iterator().next();
//
//             this.closeDatabaseNow(dbname);
//
//             DBRunner r = dbrmap.get(dbname);
//             try {
//                 // stop the db runner thread:
//                 r.q.put(new DBQuery());
//             } catch(Exception e) {
//                 Log.e(SQLitePlugin.class.getSimpleName(), "INTERNAL PLUGIN CLEANUP ERROR: could not stop db thread due to exception", e);
//             }
//             dbrmap.remove(dbname);
//         }
//     }

    // --------------------------------------------------------------------------
    // LOCAL METHODS
    // --------------------------------------------------------------------------

//     private void startDatabase(String dbname, JSONObject options, CallbackContext cbc) {
//         DBRunner r = dbrmap.get(dbname);
//
//         if (r != null) {
//             // NO LONGER EXPECTED due to BUG 666 workaround solution:
//             cbc.error("INTERNAL ERROR: database already open for db name: " + dbname);
//         } else {
//             r = new DBRunner(dbname, options, cbc);
//             dbrmap.put(dbname, r);
//             this.cordova.getThreadPool().execute(r);
//         }
//     }
//     /**
//      * Open a database.
//      *
//      * @param dbName   The name of the database file
//      */
//     private SQLiteAndroidDatabase openDatabase(String dbname, String key, CallbackContext cbc, boolean old_impl) throws Exception {
//         try {
//             // ASSUMPTION: no db (connection/handle) is already stored in the map
//             // [should be true according to the code in DBRunner.run()]
//
//             File dbfile = this.cordova.getActivity().getDatabasePath(dbname);
//
//             if (!dbfile.exists()) {
//                 dbfile.getParentFile().mkdirs();
//             }
//
//             Log.v("info", "Open sqlite db: " + dbfile.getAbsolutePath());
//
//             SQLiteAndroidDatabase mydb = new SQLiteAndroidDatabase();
//             mydb.open(dbfile, key);
//
//             // NOTE: NO Android locking/closing BUG workaround needed here
//             cbc.success();
//
//             return mydb;
//         } catch (Exception e) {
//             // NOTE: NO Android locking/closing BUG workaround needed here
//             cbc.error("can't open database " + e);
//             throw e;
//         }
//     }

    // NOTE: createFromAssets (pre-populated DB) feature is not
    // supported for SQLCipher.

//     /**
//      * Close a database (in another thread).
//      *
//      * @param dbName   The name of the database file
//      */
//     private void closeDatabase(String dbname, CallbackContext cbc) {
//         DBRunner r = dbrmap.get(dbname);
//         if (r != null) {
//             try {
//                 r.q.put(new DBQuery(false, cbc));
//             } catch(Exception e) {
//                 if (cbc != null) {
//                     cbc.error("couldn't close database" + e);
//                 }
//                 Log.e(SQLitePlugin.class.getSimpleName(), "couldn't close database", e);
//             }
//         } else {
//             if (cbc != null) {
//                 cbc.success();
//             }
//         }
//     }

//     /**
//      * Close a database (in the current thread).
//      *
//      * @param dbname   The name of the database file
//      */
//     private void closeDatabaseNow(String dbname) {
//         DBRunner r = dbrmap.get(dbname);
//
//         if (r != null) {
//             SQLiteAndroidDatabase mydb = r.mydb;
//
//             if (mydb != null)
//                 mydb.closeDatabaseNow();
//         }
//     }
//
//     private void deleteDatabase(String dbname, CallbackContext cbc) {
//         DBRunner r = dbrmap.get(dbname);
//         if (r != null) {
//             try {
//                 r.q.put(new DBQuery(true, cbc));
//             } catch(Exception e) {
//                 if (cbc != null) {
//                     cbc.error("couldn't close database" + e);
//                 }
//                 Log.e(SQLitePlugin.class.getSimpleName(), "couldn't close database", e);
//             }
//         } else {
//             boolean deleteResult = this.deleteDatabaseNow(dbname);
//             if (deleteResult) {
//                 cbc.success();
//             } else {
//                 cbc.error("couldn't delete database");
//             }
//         }
//     }
//
//     /**
//      * Delete a database.
//      *
//      * @param dbName   The name of the database file
//      *
//      * @return true if successful or false if an exception was encountered
//      */
//     private boolean deleteDatabaseNow(String dbname) {
//         File dbfile = this.cordova.getActivity().getDatabasePath(dbname);
//
//         try {
//             return cordova.getActivity().deleteDatabase(dbfile.getAbsolutePath());
//         } catch (Exception e) {
//             Log.e(SQLitePlugin.class.getSimpleName(), "couldn't delete database", e);
//             return false;
//         }
//     }

//     private class DBRunner implements Runnable {
//         final String dbname;
//         final String dbkey;
//
//         final BlockingQueue<DBQuery> q;
//         final CallbackContext openCbc;
//
//         SQLiteAndroidDatabase mydb;
//
//         DBRunner(final String dbname, JSONObject options, CallbackContext cbc) {
//             this.dbname = dbname;
//
//             String key = ""; // (no encryption by default)
//             if (options.has("key")) {
//                 try {
//                     key = options.getString("key");
//                 } catch (JSONException e) {
//                     // NOTE: this should not happen!
//                     Log.e(SQLitePlugin.class.getSimpleName(), "unexpected JSON error getting password key, ignored", e);
//                 }
//             }
//             this.dbkey = key;
//
//             this.q = new LinkedBlockingQueue<DBQuery>();
//             this.openCbc = cbc;
//         }
//
//         public void run() {
//             try {
//                 this.mydb = openDatabase(dbname, this.dbkey, this.openCbc, false);
//             } catch (Exception e) {
//                 Log.e(SQLitePlugin.class.getSimpleName(), "unexpected error, stopping db thread", e);
//                 dbrmap.remove(dbname);
//                 return;
//             }
//
//             DBQuery dbq = null;
//
//             try {
//                 dbq = q.take();
//
//                 while (!dbq.stop) {
//                     mydb.executeSqlBatch(dbq.queries, dbq.jsonparams, dbq.cbc);
//
//                     dbq = q.take();
//                 }
//             } catch (Exception e) {
//                 Log.e(SQLitePlugin.class.getSimpleName(), "unexpected error", e);
//             }
//
//             if (dbq != null && dbq.close) {
//                 try {
//                     closeDatabaseNow(dbname);
//
//                     dbrmap.remove(dbname); // (should) remove ourself
//
//                     if (!dbq.delete) {
//                         dbq.cbc.success();
//                     } else {
//                         try {
//                             boolean deleteResult = deleteDatabaseNow(dbname);
//                             if (deleteResult) {
//                                 dbq.cbc.success();
//                             } else {
//                                 dbq.cbc.error("couldn't delete database");
//                             }
//                         } catch (Exception e) {
//                             Log.e(SQLitePlugin.class.getSimpleName(), "couldn't delete database", e);
//                             dbq.cbc.error("couldn't delete database: " + e);
//                         }
//                     }
//                 } catch (Exception e) {
//                     Log.e(SQLitePlugin.class.getSimpleName(), "couldn't close database", e);
//                     if (dbq.cbc != null) {
//                         dbq.cbc.error("couldn't close database: " + e);
//                     }
//                 }
//             }
//         }
//     }

    private final class DBQuery {
        // XXX TODO replace with DBRunner action enum:
        final boolean stop;
        final boolean close;
        final boolean delete;
        final String[] queries;
        final JSONArray[] jsonparams;
        final CallbackContext cbc;

        DBQuery(String[] myqueries, JSONArray[] params, CallbackContext c) {
            this.stop = false;
            this.close = false;
            this.delete = false;
            this.queries = myqueries;
            this.jsonparams = params;
            this.cbc = c;
        }

        DBQuery(boolean delete, CallbackContext cbc) {
            this.stop = true;
            this.close = true;
            this.delete = delete;
            this.queries = null;
            this.jsonparams = null;
            this.cbc = cbc;
        }

        // signal the DBRunner thread to stop:
        DBQuery() {
            this.stop = true;
            this.close = false;
            this.delete = false;
            this.queries = null;
            this.jsonparams = null;
            this.cbc = null;
        }
    }

    private static enum Action {
        echoStringValue,
        open,
        close,
        delete,
        executeSqlBatch,
        backgroundExecuteSqlBatch,
    }

  static private void openDatabaseConnection(JSONArray args, CallbackContext cbc) {
    try {
//       Log.d(SQLitePlugin.class.getSimpleName(), "openDatabaseConnection, args: " + args.toString());
      final JSONObject config = args.getJSONObject(0);
      final String filename = config.getString("path");
      final int flags = config.getInt("flags");
      final String key = config.getString("key");
      Log.d(SQLitePlugin.class.getSimpleName(), "openDatabaseConnection, filename: " + filename);

      final int mydbc = SCCoreGlue.scc_open_connection(filename, flags);

      if (mydbc < 0) {
        cbc.error("open error: " + -mydbc);
      } else {
        if (SCCoreGlue.scc_key(mydbc, key) != 0) {
            throw new RuntimeException("password key error");
        }
        Log.d(SQLitePlugin.class.getSimpleName(), "openDatabaseConnection successful! mydbc connectionId: " + mydbc);
        cbc.success(mydbc);
      }
    } catch(Exception e) {
      // NOT EXPECTED - internal error:
      cbc.error(e.toString());
    }
  }

  static private void executeBatch(JSONArray args, CallbackContext cbc) {
    try {

    JSONObject allargs = args.getJSONObject(0);
    JSONArray txargs = allargs.getJSONArray("executes");
    Log.d(SQLitePlugin.class.getSimpleName(), "allargs: " + allargs);
    Log.d(SQLitePlugin.class.getSimpleName(), "txargs: " + txargs);
    JSONObject dbargs = allargs.getJSONObject("dbargs");
    String dbname = dbargs.getString("dbname");
    int connectionId = dbargs.getInt("connectionId");

//       final int mydbc = args.getInt(0);
      final int mydbc = connectionId;

//       JSONArray data = args.getJSONArray(1);
      JSONArray data = txargs;

      JSONArray results = new JSONArray();
      final int count = data.length();

      for (int i=0; i<count; ++i) {
//         JSONArray sa = data.getJSONArray(i);
        JSONObject sa = data.getJSONObject(i);
        String query = sa.getString("sql");
        JSONArray params = sa.getJSONArray("params");
        Log.d(SQLitePlugin.class.getSimpleName(), "query: " + query);
        Log.d(SQLitePlugin.class.getSimpleName(), "params: " + params);
//         for (int i = 0; i < len; i++) {
//             JSONObject a = txargs.getJSONObject(i);
//             queries[i] = a.getString("sql");
//             jsonparams[i] = a.getJSONArray("params");
//         }

//         String s = sa.getString(0);

//         if (SCCoreGlue.scc_begin_statement(mydbc, s) != 0) {
        if (SCCoreGlue.scc_begin_statement(mydbc, query) != 0) {
          JSONObject result = new JSONObject();
          result.put("status", 1); // REPORT SQLite
          result.put("message", SCCoreGlue.scc_get_last_error_message(mydbc));
          results.put(result);
          Log.d(SQLitePlugin.class.getSimpleName(), "scc_begin_statement result: " + result);
        } else {
//           JSONArray aa = sa.getJSONArray(1);
          Log.d(SQLitePlugin.class.getSimpleName(), "executeBatch: processing the params ");
          JSONArray aa = params;
          final int aalength = aa.length();

          int br = 0; // SQLite OK

          for (int ai = 0; ai < aalength; ++ai) {
            final Object o = aa.get(ai);

            if (o instanceof Integer || o instanceof Long) {
              br = SCCoreGlue.scc_bind_long(mydbc, 1 + ai, aa.optLong(ai));
            } else if (o instanceof Number) {
              br = SCCoreGlue.scc_bind_double(mydbc, 1 + ai, aa.optDouble(ai));
            } else if (o instanceof String) {
              br = SCCoreGlue.scc_bind_text(mydbc, 1 + ai, o.toString());
            } else {
              br = SCCoreGlue.scc_bind_null(mydbc, 1 + ai);
            }
          }

          if (br != 0) {
            JSONObject result = new JSONObject();
            result.put("status", 1); // REPORT SQLite
            result.put("message", SCCoreGlue.scc_get_last_error_message(mydbc));
            results.put(result);
            SCCoreGlue.scc_end_statement(mydbc);
            Log.d(SQLitePlugin.class.getSimpleName(), "scc_get_last_error_message result: " + result);
            continue;
          }

          final int sr = SCCoreGlue.scc_step(mydbc);

          if (sr == 100) {
            JSONArray rows = new JSONArray();

            int sr2;

            do {
              JSONObject row = new JSONObject();

              final int cc = SCCoreGlue.scc_get_column_count(mydbc);

              for (int c=0; c < cc; ++c) {
                final String c1 = SCCoreGlue.scc_get_column_name(mydbc, c);
                final int ct = SCCoreGlue.scc_get_column_type(mydbc, c);

                if (ct == SCCoreGlue.SCC_COLUMN_TYPE_INTEGER) {
                  row.put(c1, SCCoreGlue.scc_get_column_long(mydbc, c));
                } else if (ct == SCCoreGlue.SCC_COLUMN_TYPE_FLOAT) {
                  row.put(c1, SCCoreGlue.scc_get_column_double(mydbc, c));
                } else if (ct == SCCoreGlue.SCC_COLUMN_TYPE_NULL) {
                  row.put(c1, JSONObject.NULL);
                } else {
                  row.put(c1, SCCoreGlue.scc_get_column_text(mydbc, c));
                }
              }

              rows.put(row);

              sr2 = SCCoreGlue.scc_step(mydbc);
            } while (sr2 == 100);

            JSONObject result = new JSONObject();
            result.put("status", 0); // REPORT SQLite OK
            result.put("rows", rows);
            results.put(result);
            SCCoreGlue.scc_end_statement(mydbc);
          } else if (sr == 101) {
            JSONObject result = new JSONObject();
            result.put("status", 0); // REPORT SQLite OK
            result.put("total_changes", SCCoreGlue.scc_get_total_changes(mydbc));
            result.put("last_insert_rowid", SCCoreGlue.scc_get_last_insert_rowid(mydbc));
            results.put(result);
            SCCoreGlue.scc_end_statement(mydbc);
          } else {
            JSONObject result = new JSONObject();
            result.put("status", 1); // REPORT SQLite
            result.put("message", SCCoreGlue.scc_get_last_error_message(mydbc));
            results.put(result);
            SCCoreGlue.scc_end_statement(mydbc);
          }
        }
      }
       Log.d(SQLitePlugin.class.getSimpleName(), "sqlite transaction  results: " + results);
      // send results to JavaScript side (...)
      cbc.success(results);
    } catch(Exception e) {
      // NOT EXPECTED - internal error:
      Log.e(SQLitePlugin.class.getSimpleName(), "sqlite transaction  error: " + e.toString());
      cbc.error(e.toString());
    }
  }

  static {
    System.loadLibrary("sqlcipher-connection-core-glue");
    SCCoreGlue.scc_init();
  }
}
