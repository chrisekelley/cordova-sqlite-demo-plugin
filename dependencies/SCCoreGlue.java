/* !---- DO NOT EDIT: This file autogenerated by com/jogamp/gluegen/JavaEmitter.java on Tue Apr 28 13:01:03 EDT 2020 ----! */

package io.sqlc;

//import com.jogamp.gluegen.runtime.*;
//import com.jogamp.common.os.*;
//import com.jogamp.common.nio.*;
//import java.nio.*;

public class SCCoreGlue {

  public static final int SCC_COLUMN_TYPE_INTEGER = 1;
  public static final int SCC_COLUMN_TYPE_FLOAT = 2;
  public static final int SCC_COLUMN_TYPE_TEXT = 3;
  public static final int SCC_COLUMN_TYPE_NULL = 5;

  /** Interface to C language function: <br> <code> int scc_begin_statement(int connection_id, const char *  statement); </code>    */
  public static native int scc_begin_statement(int connection_id, String statement);

  /** Interface to C language function: <br> <code> int scc_bind_double(int connection_id, int index, double value); </code>    */
  public static native int scc_bind_double(int connection_id, int index, double value);

  /** Interface to C language function: <br> <code> int scc_bind_long(int connection_id, int index, scc_long_long value); </code>    */
  public static native int scc_bind_long(int connection_id, int index, long value);

  /** Interface to C language function: <br> <code> int scc_bind_null(int connection_id, int index); </code>    */
  public static native int scc_bind_null(int connection_id, int index);

  /** Interface to C language function: <br> <code> int scc_bind_text(int connection_id, int index, const char *  text); </code>    */
  public static native int scc_bind_text(int connection_id, int index, String text);

  /** Interface to C language function: <br> <code> int scc_end_statement(int connection_id); </code>    */
  public static native int scc_end_statement(int connection_id);

  /** Interface to C language function: <br> <code> int scc_get_column_count(int connection_id); </code>    */
  public static native int scc_get_column_count(int connection_id);

  /** Interface to C language function: <br> <code> double scc_get_column_double(int connection_id, int column); </code>    */
  public static native double scc_get_column_double(int connection_id, int column);

  /** Interface to C language function: <br> <code> scc_long_long scc_get_column_long(int connection_id, int column); </code>    */
  public static native long scc_get_column_long(int connection_id, int column);

  /** Interface to C language function: <br> <code> const char *  scc_get_column_name(int connection_id, int column); </code>    */
  public static native String scc_get_column_name(int connection_id, int column);

  /** Interface to C language function: <br> <code> const char *  scc_get_column_text(int connection_id, int column); </code>    */
  public static native String scc_get_column_text(int connection_id, int column);

  /** Interface to C language function: <br> <code> int scc_get_column_type(int connection_id, int column); </code>    */
  public static native int scc_get_column_type(int connection_id, int column);

  /** Interface to C language function: <br> <code> const char *  scc_get_last_error_message(int connection_id); </code>    */
  public static native String scc_get_last_error_message(int connection_id);

  /** Interface to C language function: <br> <code> int scc_get_last_insert_rowid(int connection_id); </code>    */
  public static native int scc_get_last_insert_rowid(int connection_id);

  /** Interface to C language function: <br> <code> int scc_get_total_changes(int connection_id); </code>    */
  public static native int scc_get_total_changes(int connection_id);

  /** Interface to C language function: <br> <code> void scc_init(); </code>    */
  public static native void scc_init();

  /** Interface to C language function: <br> <code> int scc_key(int connection_id, const char *  key); </code>    */
  public static native int scc_key(int connection_id, String key);

  /** Interface to C language function: <br> <code> int scc_open_connection(const char *  filename, int flags); </code>    */
  public static native int scc_open_connection(String filename, int flags);

  /** Interface to C language function: <br> <code> int scc_step(int connection_id); </code>    */
  public static native int scc_step(int connection_id);


} // end of class SCCoreGlue
