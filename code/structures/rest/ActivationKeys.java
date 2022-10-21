//===================================================================
// ActivationKeys.java
// 	Description:
// 		This is a container class for holding activation key
// 		data when imported from the rest/activation_keys.json
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.rest;

import java.math.BigInteger;

public class ActivationKeys
{
	Key[] data;

	public int count() { return data.length; }
	public boolean expired(int key) { return data[key].expired(); }
	public String key(int key) { return data[key].rawKey(); }
	public String type(int key) { return data[key].type(); }

	public class Key
	{
		int id;
		String action;
		boolean current;
		String expires_at;
		String key_type;
		String raw_key;
		String created_at;
		String updated_at;
		int key_data;
		BigInteger decoded_key_data;
		String decoded_key_data_str;

		public boolean expired() { return !current; }
		public String rawKey() { return raw_key; }
		public String type() { return key_type; }

	}
}
