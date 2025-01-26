package fr.istic.vv;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class TLSSocketFactoryTestMocks {

    /**
     * Ensures that `setEnabledProtocols` is never invoked when both supported and enabled protocols are `null`
     */
    @Test
    public void prepareSocket_NullProtocols() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(null);
        when(socket.getEnabledProtocols()).thenReturn(null);

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket, never()).setEnabledProtocols(any());
    }

    /**
     * Validates that enabled protocols are correctly set in the preferred order when both supported and enabled protocols are provided.
     */
    @Test
    public void prepareSocket_TypicalCase() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(
                new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}
        );
        when(socket.getEnabledProtocols()).thenReturn(
                new String[]{"SSLv3", "TLSv1"}
        );

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"});
    }

    /**
     * Tests behavior when there are supported protocols but no enabled ones. The enabled protocols should follow the supported protocols' preference.
     */
    @Test
    public void prepareSocket_OnlySupportedProtocols() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(
                new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}
        );
        when(socket.getEnabledProtocols()).thenReturn(null);

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"});
    }

    /**
     * Ensures that when only enabled protocols exist, they remain unchanged.
      */
    @Test
    public void prepareSocket_OnlyEnabledProtocols() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(null);
        when(socket.getEnabledProtocols()).thenReturn(
                new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}
        );

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket).setEnabledProtocols(new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"});
    }

    /**
     * Tests the edge case where both supported and enabled protocols are empty. The behavior should avoid calling `setEnabledProtocols`.
     */
    @Test
    public void prepareSocket_NoProtocols() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(new String[]{});
        when(socket.getEnabledProtocols()).thenReturn(new String[]{});

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket).setEnabledProtocols(new String[]{});
    }


    /**
     * Simulates a scenario where supported and enabled protocols overlap, ensuring the final protocol list respects the preference order.
     */
    @Test
    public void prepareSocket_ProtocolsOverlap() {
        // Arrange
        TLSSocketFactory factory = new TLSSocketFactory();
        SSLSocket socket = mock(SSLSocket.class);

        // When
        when(socket.getSupportedProtocols()).thenReturn(
                new String[]{"TLSv1.1", "TLSv1.2", "TLS"}
        );
        when(socket.getEnabledProtocols()).thenReturn(
                new String[]{"TLSv1", "TLS"}
        );

        // Then
        factory.prepareSocket(socket);

        // Verify
        verify(socket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1", "TLS"});
    }


}