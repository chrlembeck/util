package de.chrlembeck.util.swing.icon;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javax.swing.Icon;

/**
 * Icons zur Darstellung von Navigationselementen.
 *
 * @author Christoph Lembeck
 */
public class NavigationIcon implements Icon {

    /**
     * Legt Richtung und Aussehen der Navigationskomponente fest.
     * 
     * @author Christoph Lembeck
     *
     */
    public enum Direction {

        /**
         * Entspricht der Navigation an den Anfang.
         */
        FIRST,

        /**
         * Entspricht der Navigation in schnellen Schritten an den Anfang.
         */
        FIRST_DOUBLE,

        /**
         * Navigation zum Vorgänger.
         */
        PREVIOUS,

        /**
         * Schnelle Navigation in Richtung des Vorgängers.
         */
        PREVIOUS_DOUBLE,

        /**
         * Navigation zum Nachfolger.
         */
        NEXT,

        /**
         * Schnelle Navigation in Richtung des Nachfolgers.
         */
        NEXT_DOUBLE,

        /**
         * Navigation zum Ende.
         */
        LAST,

        /**
         * Schnelle Navigation zum Ende.
         */
        LAST_DOUBLE
    }

    /**
     * Größe des Icons in Pixeln.
     */
    private int size;

    /**
     * Füllstil für das Zeichnen des Rahmens.
     */
    private Paint borderPaint;

    /**
     * Richtung, in die das Navigationsicon zeigen soll.
     */
    private Direction direction;

    /**
     * Linienstärke zum Zeichnender Icons.
     */
    private float lineThickness;

    /**
     * Füllung der Navigationspfeile.
     */
    private Paint fillPaint;

    /**
     * Erstell ein neues Icon nach den übergebenen Werten.
     * 
     * @param size
     *            Größe des Icons in Pixeln.
     * @param direction
     *            Richtung, in die das Navigationsicon zeigen soll.
     * @param borderPaint
     *            Füllstil für das Zeichnen des Rahmens.
     * @param lineThickness
     *            Linienstärke zum Zeichnender Icons.
     * @param fillPaint
     *            Füllung der Navigationspfeile.
     */
    public NavigationIcon(final int size, final Direction direction, final Paint borderPaint, final float lineThickness,
            final Paint fillPaint) {
        this.size = size;
        this.borderPaint = borderPaint;
        this.direction = direction;
        this.lineThickness = lineThickness;
        this.fillPaint = fillPaint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component component, final Graphics graphics, final int xPos, final int yPos) {
        final Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.translate(xPos, yPos);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        final float halfSize = size / 2f;
        final float triangleHeight = halfSize - lineThickness;
        float yTop = halfSize - triangleHeight / 2;
        float yBottom = halfSize + triangleHeight / 2;
        float triangleWidth = (size - 2 * lineThickness) / 2f - lineThickness;
        if (direction == Direction.FIRST_DOUBLE || direction == Direction.PREVIOUS_DOUBLE) {
            final Line2D line = new Line2D.Double(lineThickness / 2, yTop, lineThickness / 2, yBottom);
            // line abstand abstand
            // | lineThickness | lineThickness/2 | dreieck | lineThickness/2 | dreieck
            final Path2D leftTriangle = new Path2D.Double(Path2D.WIND_NON_ZERO, 3);
            float xOffset = direction == Direction.FIRST_DOUBLE ? lineThickness : 0;

            leftTriangle.moveTo(xOffset + lineThickness, halfSize);
            leftTriangle.lineTo(xOffset + lineThickness + triangleWidth, yTop);
            leftTriangle.lineTo(xOffset + lineThickness + triangleWidth, yBottom);
            leftTriangle.closePath();
            final Path2D rightTriangle = new Path2D.Double(Path2D.WIND_NON_ZERO, 3);
            xOffset = direction == Direction.FIRST_DOUBLE ? 0 : lineThickness;
            rightTriangle.moveTo(size - xOffset - lineThickness / 2 - triangleWidth, halfSize);
            rightTriangle.lineTo(size - xOffset - lineThickness / 2, yTop);
            rightTriangle.lineTo(size - xOffset - lineThickness / 2, yBottom);
            rightTriangle.closePath();
            g2d.setPaint(fillPaint);
            g2d.fill(leftTriangle);
            g2d.fill(rightTriangle);
            g2d.setPaint(borderPaint);
            if (direction == Direction.FIRST_DOUBLE) {
                g2d.draw(line);
            }
            g2d.draw(leftTriangle);
            g2d.draw(rightTriangle);
        }
        if (direction == Direction.LAST_DOUBLE || direction == Direction.NEXT_DOUBLE) {
            final Line2D line = new Line2D.Double(size - lineThickness / 2, yTop, size - lineThickness / 2, yBottom);
            // abstand abstand line
            // | lineThickness/2 | dreieck | lineThickness/2 | dreieck | lineThickness
            final Path2D leftTriangle = new Path2D.Double(Path2D.WIND_NON_ZERO, 3);
            float xOffset = direction == Direction.LAST_DOUBLE ? 0 : lineThickness;
            leftTriangle.moveTo(xOffset + lineThickness / 2, yTop);
            leftTriangle.lineTo(xOffset + lineThickness / 2 + triangleWidth, halfSize);
            leftTriangle.lineTo(xOffset + lineThickness / 2, yBottom);
            leftTriangle.closePath();
            final Path2D rightTriangle = new Path2D.Double(Path2D.WIND_NON_ZERO, 3);
            xOffset = direction == Direction.LAST_DOUBLE ? lineThickness : 0;
            rightTriangle.moveTo(size - xOffset - 2 * lineThickness / 2 - triangleWidth, yTop);
            rightTriangle.lineTo(size - xOffset - 2 * lineThickness / 2, halfSize);
            rightTriangle.lineTo(size - xOffset - 2 * lineThickness / 2 - triangleWidth, yBottom);
            rightTriangle.closePath();
            g2d.setPaint(fillPaint);
            g2d.fill(leftTriangle);
            g2d.fill(rightTriangle);
            g2d.setPaint(borderPaint);
            if (direction == Direction.LAST_DOUBLE) {
                g2d.draw(line);
            }
            g2d.draw(leftTriangle);
            g2d.draw(rightTriangle);
        }
        yTop = lineThickness;
        yBottom = size - lineThickness;

        triangleWidth = size - 3 * lineThickness;
        if (direction == Direction.FIRST || direction == Direction.PREVIOUS) {
            final Line2D line = new Line2D.Double(lineThickness / 2, yTop, lineThickness / 2, yBottom);
            final Path2D triangle = new Path2D.Double();
            final float xOffset = direction == Direction.FIRST ? lineThickness : 0;
            triangle.moveTo(xOffset + lineThickness * 3 / 2, halfSize);
            triangle.lineTo(xOffset + lineThickness * 3 / 2 + triangleWidth, yTop);
            triangle.lineTo(xOffset + lineThickness * 3 / 2 + triangleWidth, yBottom);
            triangle.closePath();
            g2d.setPaint(fillPaint);
            g2d.fill(triangle);
            g2d.setPaint(borderPaint);
            if (direction == Direction.FIRST) {
                g2d.draw(line);
            }
            g2d.draw(triangle);
        }
        if (direction == Direction.LAST || direction == Direction.NEXT) {
            final Line2D line = new Line2D.Double(size - lineThickness / 2, yTop, size - lineThickness / 2, yBottom);
            final Path2D triangle = new Path2D.Double();
            final float xOffset = direction == Direction.LAST ? 0 : lineThickness;
            triangle.moveTo(xOffset + lineThickness / 2, yTop);
            triangle.lineTo(xOffset + lineThickness / 2 + triangleWidth, halfSize);
            triangle.lineTo(xOffset + lineThickness / 2, yBottom);
            triangle.closePath();
            g2d.setPaint(fillPaint);
            g2d.fill(triangle);
            g2d.setPaint(borderPaint);
            if (direction == Direction.LAST) {
                g2d.draw(line);
            }
            g2d.draw(triangle);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight() {
        return size;
    }
}