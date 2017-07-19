package com.example.demo.mina.config;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by LiLei on 2017/7/4.
 */
public class ByteArrayDecoder extends ProtocolDecoderAdapter {

    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        int limit = ioBuffer.limit();
        byte[] bytes = new byte[limit];

        ioBuffer.get(bytes);

        protocolDecoderOutput.write(bytes);
    }
}