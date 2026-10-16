/**
 * KryoCodec.java
 */
package kr.pe.util.io.que.v2.codec;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * <br>
 * Copyright 2025 gbrain All rights reserved.<br>
 * <br>
 * 
 * @author admin
 * @version
 * @since 2025. 4. 18.
 */
public class KryoCodec implements Codec {
	private ThreadLocal<Kryo> serializer = new ThreadLocal<Kryo>();
	private ThreadLocal<Output> output = new ThreadLocal<Output>();
	private ThreadLocal<Input> input = new ThreadLocal<Input>();
	private Class<?> type;

	public byte[] encode(Object element) {
		if (this.type == null) {
			this.type = element.getClass();
		}
		Kryo kryo = this.serializer.get();
		Output output = this.output.get();
		if (kryo == null) {
			kryo = new Kryo();
			kryo.setRegistrationRequired(false);
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
			this.serializer.set(kryo);
		}
		if (output == null) {
			output = new Output(1024, -1);
			this.output.set(output);
		}
		output.clear();
		kryo.writeObject(output, element);
		return output.toBytes();
	}

	public Object decode(byte[] bytes) {
		Kryo kryo = this.serializer.get();
		Input input = this.input.get();
		if (kryo == null) {
			kryo = new Kryo();
			kryo.setRegistrationRequired(false);
			this.serializer.set(kryo);
		}
		if (input == null) {
			input = new Input();
			this.input.set(input);
		}
		input.setBuffer(bytes);
		return kryo.readObject(input, this.type);
//		return kryo.readClassAndObject(input);
	}
}
